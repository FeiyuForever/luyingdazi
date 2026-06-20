<template>
  <view class="tab-bar">
    <view class="tab-item" v-for="(tab, idx) in tabs" :key="idx"
          :class="{ active: current === idx }" @click="switchTab(idx)">
      <text class="tab-icon">{{ tab.icon }}</text>
      <text class="tab-text">{{ tab.text }}</text>
    </view>
  </view>
</template>

<script>
export default {
  props: {
    current: { type: Number, default: 0 }
  },
  data() {
    return {
      tabs: [
        { icon: '🏠', text: '首页', path: '/pages/index/index' },
        { icon: '🔍', text: '发现', path: '/pages/discover/discover' },
        { icon: '🏕️', text: '组队', path: '/pages/activity/activity' },
        { icon: '💬', text: '消息', path: '/pages/message/message' },
        { icon: '👤', text: '我的', path: '/pages/mine/mine' }
      ]
    }
  },
  methods: {
    switchTab(idx) {
      if (idx === this.current) return
      uni.switchTab({ url: this.tabs[idx].path })
    }
  }
}
</script>

<style scoped>
.tab-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: 110rpx;
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: space-around;
  border-top: 1rpx solid #eee;
  padding-bottom: env(safe-area-inset-bottom);
  z-index: 999;
}
.tab-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  flex: 1;
}
.tab-icon { font-size: 40rpx; margin-bottom: 4rpx; }
.tab-text { font-size: 20rpx; color: #999; }
.tab-item.active .tab-text { color: #2b9939; font-weight: 500; }
</style>
