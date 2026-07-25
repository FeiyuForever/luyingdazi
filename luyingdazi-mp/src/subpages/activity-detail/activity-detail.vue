<template>
  <view class="container">
    <view class="card" v-if="activity">
      <image v-if="activity.coverImage" :src="activity.coverImage" mode="aspectFill" class="cover" />
      <text class="title">{{ activity.title }}</text>
      <view class="info-row">📍 {{ activity.locationName }}</view>
      <view class="info-row">🕐 {{ activity.startTime }} ~ {{ activity.endTime }}</view>
      <view class="info-row">👥 {{ activity.currentMembers }}{{ activity.maxMembers > 0 ? '/' + activity.maxMembers : '' }} 人参加</view>
      <view class="info-row">💰 {{ activity.feeDesc }}</view>
      <view class="info-row" v-if="activity.requirement">📋 {{ activity.requirement }}</view>
      <text class="desc">{{ activity.description }}</text>
      <view class="btn-outline join-btn" v-if="isCreator">我发起的活动</view>
      <view class="btn-outline join-btn" @click="handleQuit" v-else-if="joined">取消报名</view>
      <view class="btn-primary join-btn" @click="handleJoin" v-else-if="activity.status === 1">报名参加</view>
      <view class="btn-outline join-btn" v-else>{{ statusText }}</view>
    </view>
  </view>
</template>

<script>
import { getActivityDetail, isActivityJoined, joinActivity, quitActivity } from '@/api/activity'

export default {
  data() { return { activity: null, joined: false } },
  computed: {
    isCreator() {
      const userId = uni.getStorageSync('userInfo')?.id
      return Boolean(this.activity && userId && this.activity.userId === userId)
    },
    statusText() {
      const m = { 2: '已满员', 3: '进行中', 4: '已结束', 0: '已取消' }
      return m[this.activity?.status] || ''
    }
  },
  onLoad(options) { this.loadDetail(options.id) },
  methods: {
    async loadDetail(id) {
      this.activity = await getActivityDetail(id)
      try {
        this.joined = await isActivityJoined(id)
      } catch (e) {
        this.joined = false
      }
    },
    async handleJoin() {
      try {
        await joinActivity(this.activity.id)
        uni.showToast({ title: '报名成功！', icon: 'success' })
        this.activity.currentMembers++
        this.joined = true
      } catch (e) {}
    },
    async handleQuit() {
      try {
        await quitActivity(this.activity.id)
        this.activity.currentMembers = Math.max(0, this.activity.currentMembers - 1)
        this.joined = false
        uni.showToast({ title: '已取消报名', icon: 'success' })
      } catch (e) {}
    }
  }
}
</script>

<style scoped>
.cover { width: 100%; height: 360rpx; border-radius: 12rpx; margin-bottom: 20rpx; }
.title { font-size: 36rpx; font-weight: 600; display: block; margin-bottom: 20rpx; }
.info-row { font-size: 26rpx; color: #666; margin-bottom: 12rpx; }
.desc { font-size: 28rpx; line-height: 1.7; margin-top: 20rpx; display: block; color: #333; }
.join-btn { margin-top: 40rpx; text-align: center; padding: 24rpx; font-size: 32rpx; }
</style>
