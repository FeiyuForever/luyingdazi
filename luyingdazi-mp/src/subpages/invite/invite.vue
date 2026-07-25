<template>
  <view class="container">
    <view class="invite-card card">
      <text class="title">邀请好友一起露营</text>
      <text class="desc">邀请好友注册，双方各得3天会员体验</text>

      <view class="code-box">
        <text class="code-label">我的邀请码</text>
        <text class="code-value">{{ inviteCode || 'LOADING' }}</text>
      </view>

      <view class="btn-primary copy-btn" @click="copyCode">复制邀请码</view>

      <view class="rules">
        <text class="rules-title">活动规则</text>
        <text class="rule-item">1. 好友通过你的邀请码注册并完善资料</text>
        <text class="rule-item">2. 你将获得3天会员体验</text>
        <text class="rule-item">3. 好友也将获得3天会员体验</text>
        <text class="rule-item">4. 每位用户可邀请无限好友</text>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return { inviteCode: '' }
  },
  onLoad() {
    const userInfo = uni.getStorageSync('userInfo')
    this.inviteCode = userInfo?.inviteCode || ''
  },
  methods: {
    copyCode() {
      if (!this.inviteCode) {
        uni.showToast({ title: '邀请码加载失败，请重新登录', icon: 'none' })
        return
      }
      uni.setClipboardData({
        data: this.inviteCode,
        success: () => uni.showToast({ title: '已复制', icon: 'success' })
      })
    }
  }
}
</script>

<style scoped>
.invite-card { text-align: center; padding: 60rpx 40rpx; }
.title { font-size: 36rpx; font-weight: 600; display: block; margin-bottom: 12rpx; }
.desc { font-size: 26rpx; color: #666; display: block; margin-bottom: 40rpx; }
.code-box { background: #f5f5f5; border-radius: 16rpx; padding: 30rpx; margin-bottom: 30rpx; }
.code-label { font-size: 24rpx; color: #999; display: block; margin-bottom: 12rpx; }
.code-value { font-size: 48rpx; font-weight: 700; color: #2b9939; letter-spacing: 8rpx; }
.copy-btn { margin-bottom: 40rpx; }
.rules { text-align: left; background: #fafafa; border-radius: 12rpx; padding: 24rpx; }
.rules-title { font-size: 26rpx; font-weight: 600; display: block; margin-bottom: 12rpx; }
.rule-item { font-size: 24rpx; color: #666; display: block; line-height: 2; }
</style>
