<template>
  <view class="container">
    <!-- 创建活动按钮 -->
    <view class="btn-primary create-btn" @click="goCreate">+ 发起组队</view>

    <!-- 活动列表 -->
    <view class="activity-list">
      <view class="activity-card card" v-for="item in activityList" :key="item.id"
            @click="goDetail(item.id)">
        <image class="cover" v-if="item.coverImage" :src="item.coverImage" mode="aspectFill" />
        <view class="activity-info">
          <text class="title ellipsis">{{ item.title }}</text>
          <view class="meta">
            <text>📍 {{ item.locationName }}</text>
          </view>
          <view class="meta">
            <text>🕐 {{ formatDate(item.startTime) }}</text>
          </view>
          <view class="bottom flex-between">
            <text class="members">👥 {{ item.currentMembers }}{{ item.maxMembers > 0 ? '/' + item.maxMembers : '' }}人</text>
            <text class="fee">{{ item.feeDesc }}</text>
          </view>
          <view class="status-badge" :class="'status-' + item.status">
            {{ statusText(item.status) }}
          </view>
        </view>
      </view>
    </view>

    <view class="load-more" v-if="loading"><text>加载中...</text></view>
    <view class="empty" v-if="!loading && activityList.length === 0">
      <text>暂无活动，快来发起第一个吧！</text>
    </view>
  </view>
</template>

<script>
import { getActivityList } from '@/api/activity'

export default {
  data() {
    return {
      activityList: [],
      pageNum: 1,
      pageSize: 10,
      loading: false,
      noMore: false
    }
  },
  onShow() {
    this.refreshActivities()
  },
  onReachBottom() {
    if (!this.noMore) this.loadActivities()
  },
  onPullDownRefresh() {
    this.pageNum = 1
    this.activityList = []
    this.noMore = false
    this.loadActivities().then(() => uni.stopPullDownRefresh())
  },
  methods: {
    refreshActivities() {
      this.pageNum = 1
      this.activityList = []
      this.noMore = false
      return this.loadActivities()
    },
    async loadActivities() {
      if (this.loading || this.noMore) return
      this.loading = true
      try {
        const res = await getActivityList(this.pageNum, this.pageSize)
        if (res && res.list) {
          this.activityList = [...this.activityList, ...res.list]
          this.noMore = this.activityList.length >= res.total
          this.pageNum++
        }
      } catch (e) {}
      this.loading = false
    },
    goDetail(id) {
      uni.navigateTo({ url: `/subpages/activity-detail/activity-detail?id=${id}` })
    },
    goCreate() {
      uni.navigateTo({ url: '/subpages/activity-create/activity-create' })
    },
    statusText(status) {
      const map = { 1: '报名中', 2: '已满', 3: '进行中', 4: '已结束' }
      return map[status] || ''
    },
    formatDate(time) {
      if (!time) return ''
      const d = new Date(time)
      return `${d.getMonth() + 1}月${d.getDate()}日 ${d.getHours()}:${String(d.getMinutes()).padStart(2, '0')}`
    }
  }
}
</script>

<style scoped>
.create-btn { margin-bottom: 24rpx; text-align: center; }

.activity-card { display: flex; overflow: hidden; padding: 0; }
.cover { width: 200rpx; height: 200rpx; flex-shrink: 0; }
.activity-info { flex: 1; padding: 20rpx; position: relative; }
.title { font-size: 30rpx; font-weight: 600; margin-bottom: 10rpx; display: block; }
.meta { font-size: 24rpx; color: #666; margin-bottom: 8rpx; }
.bottom { margin-top: 10rpx; }
.members { font-size: 24rpx; color: #2b9939; }
.fee { font-size: 24rpx; color: #ff9800; }

.status-badge { position: absolute; top: 16rpx; right: 16rpx; font-size: 20rpx; padding: 4rpx 12rpx; border-radius: 20rpx; }
.status-1 { background: #e8f5e9; color: #2b9939; }
.status-2 { background: #fff3e0; color: #ff9800; }
.status-3 { background: #e3f2fd; color: #1976d2; }
.status-4 { background: #f5f5f5; color: #999; }

.empty { text-align: center; padding: 100rpx 0; color: #999; }
.load-more { text-align: center; padding: 30rpx; color: #999; }
</style>
