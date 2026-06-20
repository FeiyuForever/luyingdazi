<template>
  <view class="container">
    <view class="menu-list card">
      <view class="menu-item">
        <text>🔔 消息通知</text>
        <switch :checked="notifyOn" @change="notifyOn = !notifyOn" color="#2b9939" />
      </view>
      <view class="menu-item">
        <text>🔒 隐私设置</text>
        <text class="arrow">›</text>
      </view>
      <view class="menu-item">
        <text>🗑️ 清除缓存</text>
        <text class="cache-size">{{ cacheSize }}</text>
      </view>
      <view class="menu-item">
        <text>📄 用户协议</text>
        <text class="arrow">›</text>
      </view>
      <view class="menu-item">
        <text>🛡️ 隐私政策</text>
        <text class="arrow">›</text>
      </view>
      <view class="menu-item">
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
  methods: {
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
