<template>
  <view class="container">
    <!-- 未登录 -->
    <view class="login-card card" v-if="!isLogin">
      <view class="login-content">
        <text class="login-title">🏕️ 野趣搭子</text>
        <text class="login-desc">登录后发现更多露营搭子</text>
        <button class="login-btn" @click="handleLogin">微信一键登录</button>
      </view>
    </view>

    <!-- 已登录用户信息 -->
    <view class="profile-card card" v-else>
      <view class="profile-header" @click="goEditProfile">
        <image class="avatar" :src="userInfo.avatar || '/static/default-avatar.svg'" mode="aspectFill" />
        <view class="profile-info">
          <text class="nickname">{{ userInfo.nickname }}</text>
          <text class="bio">{{ userInfo.bio || '这个人很懒，什么都没写~' }}</text>
        </view>
        <text class="arrow">›</text>
      </view>
      <view class="stats flex-between">
        <view class="stat-item" @click="goMyPosts">
          <text class="stat-num">{{ userInfo.postCount || 0 }}</text>
          <text class="stat-label">动态</text>
        </view>
        <view class="stat-item" @click="goFollowList('follow')">
          <text class="stat-num">{{ userInfo.followCount || 0 }}</text>
          <text class="stat-label">关注</text>
        </view>
        <view class="stat-item" @click="goFollowList('fans')">
          <text class="stat-num">{{ userInfo.fansCount || 0 }}</text>
          <text class="stat-label">粉丝</text>
        </view>
      </view>
    </view>

    <!-- 菜单列表 -->
    <view class="menu-list card" v-if="isLogin">
      <view class="menu-item" @click="goMyPosts">
        <text>📝 我的动态</text><text class="arrow">›</text>
      </view>
      <view class="menu-item" @click="goMyActivities">
        <text>🏕️ 我的活动</text><text class="arrow">›</text>
      </view>
      <view class="menu-item" @click="goFavorites">
        <text>⭐ 我的收藏</text><text class="arrow">›</text>
      </view>
      <view class="menu-item" @click="goInvite">
        <text>📋 邀请好友</text><text class="arrow">›</text>
      </view>
      <view class="menu-item" @click="goSettings">
        <text>⚙️ 设置</text><text class="arrow">›</text>
      </view>
    </view>
  </view>
</template>

<script>
import { wxLogin as wxLoginApi, getUserInfo } from '@/api/user'
import { get } from '@/utils/request'

export default {
  data() {
    return {
      isLogin: false,
      userInfo: {}
    }
  },
  onShow() {
    const token = uni.getStorageSync('token')
    if (token) {
      this.isLogin = true
      this.loadUserInfo()
    } else {
      this.isLogin = false
    }
  },
  methods: {
    handleLogin() {
      // #ifdef MP-WEIXIN
      uni.login({
        provider: 'weixin',
        success: async (loginRes) => {
          try {
            const res = await wxLoginApi({ code: loginRes.code })
            uni.setStorageSync('token', res.token)
            uni.setStorageSync('userInfo', res.userInfo)
            this.isLogin = true
            this.userInfo = res.userInfo
            if (res.isNew) {
              uni.navigateTo({ url: '/subpages/edit-profile/edit-profile' })
            }
            uni.showToast({ title: '登录成功', icon: 'success' })
          } catch (e) {
            uni.showToast({ title: '登录失败', icon: 'none' })
          }
        },
        fail: () => {
          uni.showToast({ title: '微信登录取消', icon: 'none' })
        }
      })
      // #endif

      // #ifdef H5
      // H5 环境模拟登录（开发调试用）
      this.mockLogin()
      // #endif
    },
    async mockLogin() {
      try {
        const res = await wxLoginApi({ code: 'h5_dev_mock_code' })
        uni.setStorageSync('token', res.token)
        uni.setStorageSync('userInfo', res.userInfo)
        this.isLogin = true
        this.userInfo = res.userInfo
        uni.showToast({ title: '登录成功', icon: 'success' })
      } catch (e) {
        uni.showToast({ title: '登录失败，请确认后端已启动', icon: 'none' })
      }
    },
    async loadUserInfo() {
      try {
        const res = await getUserInfo()
        this.userInfo = res
        uni.setStorageSync('userInfo', res)
        // 加载统计数据
        this.loadStats()
      } catch (e) {}
    },
    async loadStats() {
      try {
        const counts = await get(`/api/follow/count/${this.userInfo.id}`)
        this.userInfo.followCount = counts.followCount || 0
        this.userInfo.fansCount = counts.fansCount || 0
      } catch (e) {}
      try {
        const posts = await get(`/api/post/user/${this.userInfo.id}`, { pageNum: 1, pageSize: 1 })
        this.userInfo.postCount = posts.total || 0
      } catch (e) {}
    },
    goEditProfile() {
      uni.navigateTo({ url: '/subpages/edit-profile/edit-profile' })
    },
    goMyPosts() {
      uni.navigateTo({ url: `/subpages/user-profile/user-profile?id=${this.userInfo.id}` })
    },
    goFollowList(type) {
      uni.navigateTo({ url: `/subpages/follow-list/follow-list?type=${type}&userId=${this.userInfo.id}` })
    },
    goMyActivities() {
      uni.navigateTo({ url: '/subpages/my-activities/my-activities' })
    },
    goFavorites() {
      uni.navigateTo({ url: '/subpages/favorites/favorites' })
    },
    goInvite() {
      uni.navigateTo({ url: '/subpages/invite/invite' })
    },
    goSettings() {
      uni.navigateTo({ url: '/subpages/settings/settings' })
    }
  }
}
</script>

<style scoped>
.login-card { padding: 80rpx 40rpx; text-align: center; }
.login-title { font-size: 40rpx; font-weight: 600; display: block; margin-bottom: 16rpx; }
.login-desc { font-size: 26rpx; color: #999; display: block; margin-bottom: 40rpx; }
.login-btn { background: #2b9939; color: #fff; border-radius: 40rpx; font-size: 30rpx; padding: 20rpx 0; width: 80%; }

.profile-card { padding: 30rpx 24rpx; }
.profile-header { display: flex; align-items: center; margin-bottom: 30rpx; }
.avatar { width: 120rpx; height: 120rpx; border-radius: 50%; margin-right: 24rpx; background: #eee; }
.profile-info { flex: 1; }
.nickname { font-size: 34rpx; font-weight: 600; display: block; }
.bio { font-size: 24rpx; color: #999; margin-top: 8rpx; display: block; }
.arrow { font-size: 36rpx; color: #ccc; }

.stats { border-top: 1rpx solid #f0f0f0; padding-top: 24rpx; }
.stat-item { text-align: center; flex: 1; }
.stat-num { font-size: 34rpx; font-weight: 600; display: block; }
.stat-label { font-size: 22rpx; color: #999; margin-top: 4rpx; }

.menu-list { padding: 0; }
.menu-item { display: flex; justify-content: space-between; align-items: center; padding: 28rpx 24rpx; border-bottom: 1rpx solid #f5f5f5; font-size: 28rpx; }
.menu-item:last-child { border-bottom: none; }
</style>
