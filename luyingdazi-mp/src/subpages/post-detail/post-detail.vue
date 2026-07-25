<template>
  <view class="page-container">
    <view class="card" v-if="post">
      <!-- 动态内容 -->
      <view class="user-info" @click="goProfile(post.userId)">
        <image class="avatar" :src="post.avatar || '/static/default-avatar.svg'" mode="aspectFill" />
        <view>
          <text class="nickname">{{ post.nickname || '露营新人' }}</text>
          <text class="time">{{ formatTime(post.createdAt) }}</text>
        </view>
      </view>
      <text class="content">{{ post.content }}</text>
      <view class="images" v-if="post.images && post.images.length">
        <image v-for="(img, idx) in post.images" :key="idx" :src="img"
               mode="widthFix" class="detail-img" @click="previewImage(post.images, idx)" />
      </view>
      <view class="location" v-if="post.locationName">📍 {{ post.locationName }}</view>
      <view class="actions flex-between">
        <view class="action-item" @click="handleLike">
          <text :class="{ liked: liked }">{{ liked ? '❤️' : '🩶' }} {{ post.likeCount }}</text>
        </view>
        <view class="action-item">
          <text>💬 {{ post.commentCount }}</text>
        </view>
        <view class="action-item" @click="handleFavorite">
          <text>{{ favorited ? '⭐ 已收藏' : '☆ 收藏' }}</text>
        </view>
      </view>
    </view>

    <!-- 评论区 -->
    <view class="comment-section">
      <text class="section-title">评论 ({{ comments.length }})</text>

      <view class="comment-list">
        <view class="comment-item" v-for="c in comments" :key="c.id">
          <image class="comment-avatar" :src="c.avatar || '/static/default-avatar.svg'" mode="aspectFill" />
          <view class="comment-body">
            <text class="comment-name">{{ c.nickname }}</text>
            <text class="comment-content">{{ c.content }}</text>
            <view class="comment-meta">
              <text class="comment-time">{{ formatTime(c.createdAt) }}</text>
              <text class="comment-reply" @click="replyTo(c)">回复</text>
            </view>
          </view>
        </view>

        <view class="empty-comment" v-if="comments.length === 0">
          <text>暂无评论，快来抢沙发~</text>
        </view>
      </view>
    </view>

    <!-- 底部评论输入 -->
    <view class="input-bar">
      <input class="comment-input" v-model="inputText"
             :placeholder="replyPlaceholder" confirm-type="send" @confirm="submitComment" />
      <view class="send-btn" :class="{ active: inputText }" @click="submitComment">发送</view>
    </view>
  </view>
</template>

<script>
import { getPostDetail, toggleLike } from '@/api/post'
import { checkFavorite, toggleFavorite } from '@/api/favorite'
import { get, post } from '@/utils/request'

export default {
  data() {
    return {
      post: null,
      liked: false,
      favorited: false,
      comments: [],
      inputText: '',
      replyTarget: null
    }
  },
  computed: {
    replyPlaceholder() {
      return this.replyTarget ? `回复 ${this.replyTarget.nickname}` : '写评论...'
    }
  },
  onLoad(options) {
    this.loadDetail(options.id)
    this.loadComments(options.id)
  },
  methods: {
    async loadDetail(id) {
      this.post = await getPostDetail(id)
      this.liked = this.post.liked || false
      try {
        this.favorited = await checkFavorite(id)
      } catch (e) {
        this.favorited = false
      }
    },
    async loadComments(postId) {
      try {
        const res = await get(`/api/comment/list/${postId || this.post.id}`)
        this.comments = res || []
      } catch (e) {}
    },
    async handleLike() {
      const result = await toggleLike(this.post.id)
      this.liked = result
      this.post.likeCount = Math.max(0, (this.post.likeCount || 0) + (result ? 1 : -1))
    },
    async handleFavorite() {
      this.favorited = await toggleFavorite(this.post.id)
      uni.showToast({
        title: this.favorited ? '已收藏' : '已取消收藏',
        icon: 'success'
      })
    },
    replyTo(comment) {
      this.replyTarget = comment
    },
    async submitComment() {
      if (!this.inputText.trim()) return
      try {
        const data = {
          postId: this.post.id,
          content: this.inputText.trim()
        }
        if (this.replyTarget) {
          data.parentId = this.replyTarget.id
          data.replyUserId = this.replyTarget.userId
        }
        await post('/api/comment/add', data)
        this.inputText = ''
        this.replyTarget = null
        this.post.commentCount++
        // 重新加载评论
        this.loadComments(this.post.id)
        uni.showToast({ title: '评论成功', icon: 'success' })
      } catch (e) {}
    },
    goProfile(userId) {
      uni.navigateTo({ url: `/subpages/user-profile/user-profile?id=${userId}` })
    },
    previewImage(imgs, idx) {
      uni.previewImage({ urls: imgs, current: imgs[idx] })
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
.page-container { padding-bottom: 120rpx; }

.user-info { display: flex; align-items: center; margin-bottom: 20rpx; }
.avatar { width: 80rpx; height: 80rpx; border-radius: 50%; margin-right: 16rpx; background: #eee; }
.nickname { font-size: 30rpx; font-weight: 500; display: block; }
.time { font-size: 22rpx; color: #999; }
.content { font-size: 30rpx; line-height: 1.7; margin-bottom: 20rpx; display: block; }
.detail-img { width: 100%; border-radius: 12rpx; margin-bottom: 12rpx; }
.location { font-size: 24rpx; color: #666; margin-bottom: 20rpx; }
.actions { border-top: 1rpx solid #f0f0f0; padding-top: 20rpx; }
.action-item { font-size: 26rpx; color: #666; }
.action-item .liked { color: #e53935; }

/* 评论区 */
.comment-section { background: #fff; border-radius: 16rpx; padding: 24rpx; margin-top: 20rpx; }
.section-title { font-size: 28rpx; font-weight: 600; margin-bottom: 20rpx; display: block; }

.comment-item { display: flex; margin-bottom: 24rpx; }
.comment-avatar { width: 60rpx; height: 60rpx; border-radius: 50%; margin-right: 16rpx; flex-shrink: 0; background: #eee; }
.comment-body { flex: 1; }
.comment-name { font-size: 24rpx; color: #666; font-weight: 500; display: block; margin-bottom: 6rpx; }
.comment-content { font-size: 28rpx; line-height: 1.5; display: block; margin-bottom: 8rpx; }
.comment-meta { display: flex; align-items: center; gap: 20rpx; }
.comment-time { font-size: 22rpx; color: #999; }
.comment-reply { font-size: 22rpx; color: #2b9939; }

.empty-comment { text-align: center; padding: 40rpx; color: #999; font-size: 26rpx; }

/* 底部输入栏 */
.input-bar {
  position: fixed; bottom: 0; left: 0; right: 0;
  display: flex; align-items: center;
  padding: 16rpx 20rpx; background: #fff;
  border-top: 1rpx solid #eee;
  padding-bottom: calc(16rpx + env(safe-area-inset-bottom));
}
.comment-input { flex: 1; background: #f5f5f5; border-radius: 36rpx; padding: 16rpx 24rpx; font-size: 28rpx; }
.send-btn { margin-left: 16rpx; padding: 14rpx 28rpx; background: #ddd; color: #999; border-radius: 36rpx; font-size: 26rpx; }
.send-btn.active { background: #2b9939; color: #fff; }
</style>
