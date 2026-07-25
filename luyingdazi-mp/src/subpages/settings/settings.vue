<template>
  <view class="container">
    <view class="menu-list card">
      <view class="menu-item">
        <text>🔔 消息通知</text>
        <switch :checked="notifyOn" @change="handleNotifyChange" color="#2b9939" />
      </view>
      <view class="menu-item" @click="showPrivacySettings">
        <text>🔒 隐私设置</text>
        <text class="arrow">›</text>
      </view>
      <view class="menu-item" @click="clearCache">
        <text>🗑️ 清除缓存</text>
        <text class="cache-size">{{ cacheSize }}</text>
      </view>
      <view class="menu-item" @click="showAgreement">
        <text>📄 用户协议</text>
        <text class="arrow">›</text>
      </view>
      <view class="menu-item" @click="showPrivacyPolicy">
        <text>🛡️ 隐私政策</text>
        <text class="arrow">›</text>
      </view>
      <view class="menu-item" @click="showAbout">
        <text>ℹ️ 关于我们</text>
        <text class="version">v1.0.0</text>
      </view>
    </view>

    <view class="logout-btn" @click="handleLogout">退出登录</view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      notifyOn: true,
      cacheSize: '2.3MB'
    }
  },
  onLoad() {
    const saved = uni.getStorageSync('notifyOn')
    if (saved !== '') this.notifyOn = Boolean(saved)
    this.refreshCacheSize()
  },
  methods: {
    handleNotifyChange(e) {
      this.notifyOn = e.detail.value
      uni.setStorageSync('notifyOn', this.notifyOn)
    },
    refreshCacheSize() {
      try {
        const info = uni.getStorageInfoSync()
        this.cacheSize = `${(info.currentSize / 1024).toFixed(2)}MB`
      } catch (e) {
        this.cacheSize = '0MB'
      }
    },
    clearCache() {
      uni.showModal({
        title: '清除缓存',
        content: '将清除本地偏好设置，但不会退出登录。',
        success: (res) => {
          if (!res.confirm) return
          const token = uni.getStorageSync('token')
          const userInfo = uni.getStorageSync('userInfo')
          uni.clearStorageSync()
          if (token) uni.setStorageSync('token', token)
          if (userInfo) uni.setStorageSync('userInfo', userInfo)
          this.notifyOn = true
          this.refreshCacheSize()
          uni.showToast({ title: '缓存已清除', icon: 'success' })
        }
      })
    },
    showPrivacySettings() {
      uni.showModal({
        title: '隐私设置',
        content: '位置权限可在微信“设置 > 个人信息与权限 > 系统权限管理”中修改。',
        showCancel: false
      })
    },
    showAgreement() {
      uni.showModal({
        title: '用户协议',
        content: '请文明交流，不发布违法、侵权、虚假或骚扰内容；线下活动请自行确认安全与费用。',
        showCancel: false
      })
    },
    showPrivacyPolicy() {
      uni.showModal({
        title: '隐私政策',
        content: '仅在提供登录、附近推荐、内容发布和聊天功能时处理必要信息，不出售个人信息。',
        showCancel: false
      })
    },
    showAbout() {
      uni.showModal({
        title: '野趣搭子 v1.0.0',
        content: '专注户外露营约伴与经验分享。',
        showCancel: false
      })
    },
    handleLogout() {
      uni.showModal({
        title: '提示',
        content: '确定退出登录吗？',
        success: (res) => {
          if (res.confirm) {
            uni.removeStorageSync('token')
            uni.removeStorageSync('userInfo')
            uni.reLaunch({ url: '/pages/mine/mine' })
          }
        }
      })
    }
  }
}
</script>

<style scoped>
.menu-list { padding: 0; }
.menu-item { display: flex; justify-content: space-between; align-items: center; padding: 28rpx 24rpx; border-bottom: 1rpx solid #f5f5f5; font-size: 28rpx; }
.menu-item:last-child { border-bottom: none; }
.arrow { font-size: 32rpx; color: #ccc; }
.cache-size { font-size: 24rpx; color: #999; }
.version { font-size: 24rpx; color: #999; }

.logout-btn { margin-top: 60rpx; text-align: center; padding: 24rpx; color: #e53935; font-size: 30rpx; background: #fff; border-radius: 16rpx; }
</style>
