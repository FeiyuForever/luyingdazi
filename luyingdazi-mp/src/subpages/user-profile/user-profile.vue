<template>
  <view class="container">
    <view class="profile-card card" v-if="user">
      <image class="avatar" :src="user.avatar || '/static/default-avatar.png'" mode="aspectFill" />
      <text class="nickname">{{ user.nickname }}</text>
      <text class="bio">{{ user.bio || '这个人很懒~' }}</text>
      <view class="tags" v-if="user.tags && user.tags.length">
        <text class="tag" v-for="t in user.tags" :key="t">{{ t }}</text>
      </view>
      <view class="info-row">
        <text v-if="user.city">📍 {{ user.city }}</text>
        <text v-if="user.distance"> · {{ formatDist(user.distance) }}</text>
      </view>

      <!-- 统计 -->
      <view class="stats">
        <view class="stat-item">
          <text class="stat-num">{{ followCount }}</text>
          <text class="stat-label">关注</text>
        </view>
        <view class="stat-item">
          <text class="stat-num">{{ fansCount }}</text>
          <text class="stat-label">粉丝</text>
        </view>
      </view>

      <!-- 操作按钮（不是自己才显示） -->
      <view class="btn-row" v-if="!isSelf">
        <view class="btn-primary" @click="handleChat">💬 私信</view>
        <view :class="followed ? 'btn-followed' : 'btn-outline'" @click="handleFollow">
          {{ followed ? '已关注 ✓' : '+ 关注' }}
        </view>
      </view>
      <view class="btn-row" v-else>
        <view class="btn-outline" @click="goEdit">编辑资料</view>
      </view>
    </view>

    <!-- 用户动态列表 -->
    <view class="section-title" v-if="posts.length">{{ isSelf ? '我的动态' : 'TA的动态' }}</view>
    <view class="post-card card" v-for="p in posts" :key="p.id" @click="goPostDetail(p.id)">
      <text class="post-content">{{ p.content }}</text>
      <view class="post-meta">
        <text>📍 {{ p.locationName || '' }}</text>
        <text>❤️ {{ p.likeCount }} 💬 {{ p.commentCount }}</text>
      </view>
    </view>
  </view>
</template>

<script>
import { getUserProfile } from '@/api/user'
import { get, post } from '@/utils/request'

export default {
  data() {
    return {
      user: null,
      followed: false,
      followCount: 0,
      fansCount: 0,
      posts: [],
      isSelf: false
    }
  },
  onLoad(options) {
    const myInfo = uni.getStorageSync('userInfo')
    this.isSelf = myInfo && String(myInfo.id) === String(options.id)
    this.loadProfile(options.id)
    this.loadFollowStatus(options.id)
    this.loadPosts(options.id)
  },
  methods: {
    async loadProfile(id) {
      this.user = await getUserProfile(id)
    },
    async loadFollowStatus(id) {
      try {
        this.followed = await get(`/api/follow/check/${id}`)
        const counts = await get(`/api/follow/count/${id}`)
        this.followCount = counts.followCount || 0
        this.fansCount = counts.fansCount || 0
      } catch (e) {}
    },
    async loadPosts(userId) {
      try {
        const res = await get(`/api/post/user/${userId}`, { pageNum: 1, pageSize: 10 })
        this.posts = (res && res.list) || []
      } catch (e) {}
    },
    handleChat() {
      const myInfo = uni.getStorageSync('userInfo')
      if (myInfo && myInfo.id === this.user.id) {
        uni.showToast({ title: '不能给自己发私信', icon: 'none' })
        return
      }
      uni.navigateTo({ url: `/subpages/chat/chat?targetId=${this.user.id}&name=${this.user.nickname}` })
    },
    async handleFollow() {
      try {
        const result = await post(`/api/follow/toggle/${this.user.id}`)
        this.followed = result
        this.fansCount += result ? 1 : -1
        uni.showToast({ title: result ? '已关注' : '已取消关注', icon: 'success' })
      } catch (e) {}
    },
    goEdit() {
      uni.navigateTo({ url: '/subpages/edit-profile/edit-profile' })
    },
    goPostDetail(id) {
      uni.navigateTo({ url: `/subpages/post-detail/post-detail?id=${id}` })
    },
    formatDist(km) {
      if (!km) return ''
      return km < 1 ? Math.round(km * 1000) + 'm' : km.toFixed(1) + 'km'
    }
  }
}
</script>

<style scoped>
.profile-card { text-align: center; padding: 40rpx; }
.avatar { width: 160rpx; height: 160rpx; border-radius: 50%; margin-bottom: 16rpx; background: #eee; }
.nickname { font-size: 36rpx; font-weight: 600; display: block; margin-bottom: 8rpx; }
.bio { font-size: 26rpx; color: #999; display: block; margin-bottom: 16rpx; }
.tags { margin-bottom: 16rpx; }
.tag { background: #e8f5e9; color: #2b9939; font-size: 22rpx; padding: 6rpx 16rpx; border-radius: 20rpx; margin: 0 8rpx; }
.info-row { font-size: 24rpx; color: #666; margin-bottom: 20rpx; }

.stats { display: flex; justify-content: center; gap: 60rpx; margin-bottom: 24rpx; }
.stat-item { text-align: center; }
.stat-num { font-size: 32rpx; font-weight: 600; display: block; }
.stat-label { font-size: 22rpx; color: #999; }

.btn-row { display: flex; gap: 20rpx; justify-content: center; }
.btn-followed { border: 1rpx solid #ccc; color: #999; border-radius: 40rpx; padding: 16rpx 40rpx; text-align: center; font-size: 28rpx; }

.section-title { font-size: 28rpx; font-weight: 600; padding: 20rpx 0 10rpx; }
.post-content { font-size: 28rpx; display: block; margin-bottom: 10rpx; }
.post-meta { font-size: 22rpx; color: #999; display: flex; justify-content: space-between; }
</style>
