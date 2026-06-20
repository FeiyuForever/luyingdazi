<template>
  <view class="container">
    <!-- 顶部搜索栏 -->
    <view class="search-bar">
      <text class="search-icon">🔍</text>
      <input class="search-input" v-model="searchKeyword" placeholder="搜索露营动态..."
             confirm-type="search" @confirm="doSearch" />
      <text class="search-clear" v-if="searchKeyword" @click="clearSearch">✕</text>
    </view>

    <!-- 动态列表 -->
    <view class="post-list">
      <view class="post-card card" v-for="item in postList" :key="item.id" @click="goDetail(item.id)">
        <!-- 用户信息 -->
        <view class="post-header flex-between">
          <view class="user-info" @click.stop="goProfile(item.userId)">
            <image class="avatar" :src="item.avatar || '/static/default-avatar.png'" mode="aspectFill" />
            <view>
              <text class="nickname">{{ item.nickname || '露营新人' }}</text>
              <text class="time">{{ formatTime(item.createdAt) }}</text>
            </view>
          </view>
        </view>

        <!-- 内容 -->
        <text class="post-content">{{ item.content }}</text>

        <!-- 图片 -->
        <view class="post-images" v-if="item.images && item.images.length">
          <image v-for="(img, idx) in item.images.slice(0, 3)" :key="idx"
                 class="post-img" :src="img" mode="aspectFill"
                 @click.stop="previewImage(item.images, idx)" />
          <view v-if="item.images.length > 3" class="img-more">+{{ item.images.length - 3 }}</view>
        </view>

        <!-- 定位 -->
        <view class="post-location" v-if="item.locationName">
          <text>📍 {{ item.locationName }}</text>
        </view>

        <!-- 互动栏 -->
        <view class="post-actions flex-between">
          <view class="action-item" @click.stop="handleLike(item)">
            <text :class="{ 'liked': item.liked }">{{ item.liked ? '❤️' : '🩶' }} {{ item.likeCount || 0 }}</text>
          </view>
          <view class="action-item">
            <text>💬 {{ item.commentCount || 0 }}</text>
          </view>
          <view class="action-item">
            <text>🔗 分享</text>
          </view>
        </view>
      </view>

      <!-- 加载状态 -->
      <view class="load-more" v-if="loading">
        <text>加载中...</text>
      </view>
      <view class="load-more" v-if="noMore">
        <text>— 没有更多了 —</text>
      </view>
    </view>

    <!-- 发布按钮 -->
    <view class="fab-btn" @click="goPublish">
      <text>+</text>
    </view>
  </view>
</template>

<script>
import { getPostFeed, toggleLike } from '@/api/post'

export default {
  data() {
    return {
      postList: [],
      pageNum: 1,
      pageSize: 10,
      loading: false,
      noMore: false,
      searchKeyword: ''
    }
  },
  onLoad() {
    this.loadPosts()
  },
  onReachBottom() {
    if (!this.noMore) this.loadPosts()
  },
  onPullDownRefresh() {
    this.pageNum = 1
    this.postList = []
    this.noMore = false
    this.loadPosts().then(() => uni.stopPullDownRefresh())
  },
  methods: {
    async loadPosts() {
      if (this.loading || this.noMore) return
      this.loading = true
      try {
        const params = { pageNum: this.pageNum, pageSize: this.pageSize }
        if (this.searchKeyword) params.keyword = this.searchKeyword
        const res = await getPostFeed(params.pageNum, params.pageSize, params.keyword)
        if (res && res.list) {
          this.postList = [...this.postList, ...res.list]
          this.noMore = this.postList.length >= res.total
          this.pageNum++
        }
      } catch (e) {
        console.error(e)
      }
      this.loading = false
    },
    async handleLike(item) {
      try {
        const liked = await toggleLike(item.id)
        item.liked = liked
        item.likeCount += liked ? 1 : -1
      } catch (e) {}
    },
    goDetail(id) {
      uni.navigateTo({ url: `/subpages/post-detail/post-detail?id=${id}` })
    },
    goProfile(userId) {
      uni.navigateTo({ url: `/subpages/user-profile/user-profile?id=${userId}` })
    },
    goPublish() {
      uni.navigateTo({ url: '/subpages/post-publish/post-publish' })
    },
    goSearch() {
      uni.switchTab({ url: '/pages/discover/discover' })
    },
    doSearch() {
      // 重新加载，带关键词过滤
      this.pageNum = 1
      this.postList = []
      this.noMore = false
      this.loadPosts()
    },
    clearSearch() {
      this.searchKeyword = ''
      this.doSearch()
    },
    previewImage(images, idx) {
      uni.previewImage({ urls: images, current: images[idx] })
    },
    formatTime(time) {
      if (!time) return ''
      const d = new Date(time)
      const now = new Date()
      const diff = (now - d) / 1000
      if (diff < 60) return '刚刚'
      if (diff < 3600) return Math.floor(diff / 60) + '分钟前'
      if (diff < 86400) return Math.floor(diff / 3600) + '小时前'
      return `${d.getMonth() + 1}-${d.getDate()}`
    }
  }
}
</script>

<style scoped>
.search-bar {
  display: flex;
  align-items: center;
  background: #fff;
  border-radius: 40rpx;
  padding: 16rpx 24rpx;
  margin-bottom: 20rpx;
}
.search-icon { margin-right: 12rpx; }
.search-input { flex: 1; font-size: 26rpx; }
.search-clear { color: #999; font-size: 28rpx; padding: 0 12rpx; }

.post-card { margin-bottom: 20rpx; }
.post-header { margin-bottom: 16rpx; }
.user-info { display: flex; align-items: center; }
.avatar { width: 72rpx; height: 72rpx; border-radius: 50%; margin-right: 16rpx; background: #eee; }
.nickname { font-size: 28rpx; font-weight: 500; display: block; }
.time { font-size: 22rpx; color: #999; }

.post-content { font-size: 28rpx; line-height: 1.6; margin-bottom: 16rpx; display: block; }

.post-images { display: flex; flex-wrap: wrap; gap: 8rpx; margin-bottom: 16rpx; }
.post-img { width: 220rpx; height: 220rpx; border-radius: 8rpx; background: #eee; }
.img-more { width: 220rpx; height: 220rpx; background: rgba(0,0,0,0.5); color: #fff; display: flex; align-items: center; justify-content: center; border-radius: 8rpx; font-size: 32rpx; }

.post-location { font-size: 24rpx; color: #666; margin-bottom: 16rpx; }
.post-actions { border-top: 1rpx solid #f0f0f0; padding-top: 16rpx; }
.action-item { font-size: 24rpx; color: #666; }
.action-item .liked { color: #e53935; }

.load-more { text-align: center; padding: 30rpx; color: #999; font-size: 24rpx; }

.fab-btn {
  position: fixed; right: 40rpx; bottom: 180rpx;
  width: 100rpx; height: 100rpx; border-radius: 50%;
  background: #2b9939; color: #fff; font-size: 48rpx;
  display: flex; align-items: center; justify-content: center;
  box-shadow: 0 4rpx 16rpx rgba(43,153,57,0.4);
}
</style>
