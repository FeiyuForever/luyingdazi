<template>
  <view class="chat-container">
    <!-- 消息列表 -->
    <scroll-view class="msg-list" scroll-y :scroll-top="scrollTop" :scroll-into-view="scrollToId">
      <view class="msg-item" v-for="msg in messages" :key="msg.id"
            :class="{ 'msg-mine': msg.isMine }">
        <image class="msg-avatar" :src="msg.isMine ? myAvatar : targetAvatar" mode="aspectFill" />
        <view class="msg-bubble">
          <text class="msg-text">{{ msg.content }}</text>
          <text class="msg-status" v-if="msg.pending">发送中...</text>
          <text class="msg-status failed" v-if="msg.failed">发送失败</text>
        </view>
      </view>
    </scroll-view>

    <!-- 输入栏 -->
    <view class="input-bar">
      <input class="msg-input" v-model="inputText" placeholder="输入消息..."
             confirm-type="send" @confirm="sendMsg" :adjust-position="true" />
      <view class="send-btn" :class="{ active: inputText }" @click="sendMsg">发送</view>
    </view>
  </view>
</template>

<script>
import { getMessages, sendMessage, markRead } from '@/api/chat'

export default {
  data() {
    return {
      sessionId: null,
      targetId: null,
      targetName: '',
      targetAvatar: '/static/default-avatar.svg',
      myAvatar: '/static/default-avatar.svg',
      messages: [],
      inputText: '',
      scrollTop: 0,
      scrollToId: '',
      pageNum: 1
    }
  },
  onLoad(options) {
    this.sessionId = options.sessionId
    this.targetId = options.targetId
    this.targetName = options.name || '对方'
    uni.setNavigationBarTitle({ title: this.targetName })

    const userInfo = uni.getStorageSync('userInfo')
    if (userInfo) this.myAvatar = userInfo.avatar || this.myAvatar

    this.loadMessages()
    // 标记已读
    if (this.sessionId) markRead(this.sessionId)
  },
  methods: {
    async loadMessages() {
      if (!this.sessionId || this.sessionId === 'undefined') {
        // 首次私信，还没有会话，显示空列表
        this.messages = []
        return
      }
      try {
        const res = await getMessages(this.sessionId, this.pageNum, 30)
        if (res && res.list) {
          this.messages = res.list
          this.$nextTick(() => { this.scrollTop = 99999 })
        }
      } catch (e) {}
    },
    async sendMsg() {
      if (!this.inputText.trim()) return
      const content = this.inputText.trim()
      this.inputText = ''

      // 先本地展示
      const tempMessage = {
        id: `temp-${Date.now()}`,
        senderId: 'me',
        content,
        isMine: true,
        pending: true,
        failed: false,
        createdAt: new Date().toISOString()
      }
      this.messages.push(tempMessage)
      this.$nextTick(() => { this.scrollTop = 99999 })

      // 发送到后端
      try {
        const messageId = await sendMessage({
          receiverId: this.targetId,
          msgType: 1,
          content
        })
        tempMessage.id = messageId
        tempMessage.pending = false
      } catch (e) {
        tempMessage.pending = false
        tempMessage.failed = true
        uni.showToast({ title: '发送失败', icon: 'none' })
      }
    }
  }
}
</script>

<style scoped>
.chat-container { display: flex; flex-direction: column; height: 100vh; background: #f5f5f5; }

.msg-list { flex: 1; padding: 20rpx; }
.msg-item { display: flex; margin-bottom: 24rpx; align-items: flex-start; }
.msg-item.msg-mine { flex-direction: row-reverse; }
.msg-avatar { width: 72rpx; height: 72rpx; border-radius: 50%; margin: 0 16rpx; flex-shrink: 0; background: #eee; }
.msg-bubble { max-width: 60%; background: #fff; border-radius: 16rpx; padding: 16rpx 24rpx; }
.msg-mine .msg-bubble { background: #2b9939; }
.msg-mine .msg-text { color: #fff; }
.msg-text { font-size: 28rpx; line-height: 1.5; word-break: break-all; }
.msg-status { display: block; margin-top: 6rpx; font-size: 20rpx; color: rgba(255,255,255,0.75); }
.msg-status.failed { color: #ffcccc; }

.input-bar { display: flex; align-items: center; padding: 16rpx 20rpx; background: #fff; border-top: 1rpx solid #eee; padding-bottom: calc(16rpx + env(safe-area-inset-bottom)); }
.msg-input { flex: 1; background: #f5f5f5; border-radius: 36rpx; padding: 16rpx 24rpx; font-size: 28rpx; }
.send-btn { margin-left: 16rpx; padding: 14rpx 28rpx; background: #ddd; color: #999; border-radius: 36rpx; font-size: 26rpx; }
.send-btn.active { background: #2b9939; color: #fff; }
</style>
