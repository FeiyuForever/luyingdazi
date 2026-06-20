<template>
  <view class="container">
    <view class="session-list">
      <view class="session-item card" v-for="item in sessionList" :key="item.sessionId"
            @click="goChat(item)">
        <image class="avatar" :src="item.targetAvatar || '/static/default-avatar.png'" mode="aspectFill" />
        <view class="session-info">
          <view class="flex-between">
            <text class="name ellipsis">{{ item.targetNickname }}</text>
            <text class="time">{{ formatTime(item.lastMsgTime) }}</text>
          </view>
          <view class="flex-between">
            <text class="last-msg ellipsis">{{ item.lastMsg || '暂无消息' }}</text>
            <view class="badge" v-if="item.unread > 0">{{ item.unread > 99 ? '99+' : item.unread }}</view>
          </view>
        </view>
      </view>
    </view>

    <view class="empty" v-if="!loading && sessionList.length === 0">
      <text>暂无消息</text>
      <text class="empty-sub">去发现页找搭子聊聊吧~</text>
    </view>
  </view>
</template>

<script>
import { getSessionList } from '@/api/chat'

export default {
  data() {
    return {
      sessionList: [],
      loading: false
    }
  },
  onShow() {
    this.loadSessions()
  },
  methods: {
    async loadSessions() {
      this.loading = true
      try {
        const res = await getSessionList()
        this.sessionList = res || []
      } catch (e) {}
      this.loading = false
    },
    goChat(item) {
      uni.navigateTo({
        url: `/subpages/chat/chat?sessionId=${item.sessionId}&targetId=${item.targetUserId}&name=${item.targetNickname}`
      })
    },
    formatTime(time) {
      if (!time) return ''
      const d = new Date(time)
      const now = new Date()
      const isToday = d.toDateString() === now.toDateString()
      if (isToday) return `${d.getHours()}:${String(d.getMinutes()).padStart(2, '0')}`
      return `${d.getMonth() + 1}-${d.getDate()}`
    }
  }
}
</script>

<style scoped>
.session-item { display: flex; align-items: center; padding: 20rpx 24rpx; margin-bottom: 2rpx; }
.avatar { width: 96rpx; height: 96rpx; border-radius: 50%; margin-right: 20rpx; flex-shrink: 0; background: #eee; }
.session-info { flex: 1; overflow: hidden; }
.name { font-size: 30rpx; font-weight: 500; max-width: 300rpx; }
.time { font-size: 22rpx; color: #999; }
.last-msg { font-size: 26rpx; color: #999; margin-top: 8rpx; max-width: 400rpx; }
.badge { background: #f44336; color: #fff; font-size: 20rpx; min-width: 32rpx; height: 32rpx; border-radius: 16rpx; display: flex; align-items: center; justify-content: center; padding: 0 8rpx; }

.empty { text-align: center; padding: 200rpx 0; }
.empty text { display: block; color: #999; font-size: 28rpx; }
.empty-sub { font-size: 24rpx; margin-top: 12rpx; }
</style>
