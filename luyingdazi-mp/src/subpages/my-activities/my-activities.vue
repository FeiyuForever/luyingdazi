<template>
  <view class="container">
    <view class="tab-header">
      <view class="tab" :class="{ active: tabIdx === 0 }" @click="tabIdx = 0">我参加的</view>
      <view class="tab" :class="{ active: tabIdx === 1 }" @click="tabIdx = 1">我发起的</view>
    </view>

    <view class="activity-list">
      <view class="activity-card card" v-for="item in displayList" :key="item.id"
            @click="goDetail(item.id)">
        <text class="title">{{ item.title }}</text>
        <view class="meta">📍 {{ item.locationName }}</view>
        <view class="meta">🕐 {{ item.startTime }}</view>
        <view class="bottom flex-between">
          <text class="members">👥 {{ item.currentMembers }}人</text>
          <text class="status-text" :class="'s' + item.status">{{ statusText(item.status) }}</text>
        </view>
      </view>
    </view>

    <view class="empty" v-if="displayList.length === 0">
      <text>暂无活动记录</text>
    </view>
  </view>
</template>

<script>
import { getMyActivities } from '@/api/activity'

export default {
  data() {
    return { tabIdx: 0, joinedList: [], createdList: [] }
  },
  computed: {
    displayList() { return this.tabIdx === 0 ? this.joinedList : this.createdList }
  },
  onLoad() { this.loadData() },
  methods: {
    async loadData() {
      try {
        const [joined, created] = await Promise.all([
          getMyActivities('joined'),
          getMyActivities('created')
        ])
        this.joinedList = joined || []
        this.createdList = created || []
      } catch (e) {}
    },
    goDetail(id) { uni.navigateTo({ url: `/subpages/activity-detail/activity-detail?id=${id}` }) },
    statusText(s) { return { 1: '报名中', 2: '已满', 3: '进行中', 4: '已结束', 0: '已取消' }[s] || '' }
  }
}
</script>

<style scoped>
.tab-header { display: flex; background: #fff; margin-bottom: 20rpx; border-radius: 12rpx; }
.tab { flex: 1; text-align: center; padding: 24rpx; font-size: 28rpx; color: #666; }
.tab.active { color: #2b9939; font-weight: 600; border-bottom: 4rpx solid #2b9939; }
.title { font-size: 30rpx; font-weight: 500; display: block; margin-bottom: 10rpx; }
.meta { font-size: 24rpx; color: #666; margin-bottom: 6rpx; }
.bottom { margin-top: 10rpx; }
.members { font-size: 24rpx; color: #2b9939; }
.status-text { font-size: 22rpx; padding: 4rpx 12rpx; border-radius: 20rpx; }
.s1 { background: #e8f5e9; color: #2b9939; }
.s4 { background: #f5f5f5; color: #999; }
.empty { text-align: center; padding: 100rpx 0; color: #999; }
</style>
