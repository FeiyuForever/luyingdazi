<template>
  <view class="container">
    <!-- 搜索框 -->
    <view class="search-section">
      <input class="search-input" v-model="keyword" placeholder="搜索昵称/标签"
             confirm-type="search" @confirm="doSearch" />
      <view class="btn-primary search-btn" @click="doSearch">搜索</view>
    </view>

    <!-- 筛选标签 -->
    <scroll-view scroll-x class="tag-scroll">
      <view class="tag-item" :class="{ active: selectedTag === '' }" @click="selectTag('')">全部</view>
      <view class="tag-item" :class="{ active: selectedTag === tag }"
            v-for="tag in tagList" :key="tag" @click="selectTag(tag)">
        {{ tag }}
      </view>
    </scroll-view>

    <!-- 附近的人列表 -->
    <view class="section-title">📍 附近的人</view>
    <view class="user-grid">
      <view class="user-card card" v-for="user in nearbyUsers" :key="user.id"
            @click="goProfile(user.id)">
        <image class="user-avatar" :src="user.avatar || '/static/default-avatar.svg'" mode="aspectFill" />
        <text class="user-name ellipsis">{{ user.nickname }}</text>
        <text class="user-distance">{{ formatDistance(user.distance) }}</text>
        <view class="user-tags">
          <text class="mini-tag" v-for="tag in (user.tags || []).slice(0, 2)" :key="tag">{{ tag }}</text>
        </view>
      </view>
    </view>

    <!-- 空状态 -->
    <view class="empty" v-if="!loading && nearbyUsers.length === 0">
      <text>附近暂无用户，试试扩大搜索范围</text>
    </view>

    <view class="load-more" v-if="loading">
      <text>定位中...</text>
    </view>
  </view>
</template>

<script>
import { getNearbyUsers, searchUsers } from '@/api/match'
import { reportLocation } from '@/api/user'

export default {
  data() {
    return {
      keyword: '',
      selectedTag: '',
      nearbyUsers: [],
      loading: false,
      longitude: null,
      latitude: null,
      tagList: ['自驾露营', '徒步露营', '烧烤', '星空摄影', '亲子', '钓鱼', '骑行']
    }
  },
  onLoad() {
    this.getLocation()
  },
  methods: {
    getLocation() {
      this.loading = true
      // #ifdef MP-WEIXIN
      uni.getLocation({
        type: 'gcj02',
        success: (res) => {
          this.longitude = res.longitude
          this.latitude = res.latitude
          reportLocation({ longitude: res.longitude, latitude: res.latitude })
          this.loadNearby()
        },
        fail: () => {
          this.loading = false
          uni.showToast({ title: '请允许获取位置权限', icon: 'none' })
        }
      })
      // #endif

      // #ifdef H5
      // H5 开发环境使用模拟坐标（上海）
      this.longitude = 121.4737
      this.latitude = 31.2304
      reportLocation({ longitude: this.longitude, latitude: this.latitude, city: '上海' })
      this.loadNearby()
      // #endif
    },
    async loadNearby() {
      try {
        const res = await getNearbyUsers({
          longitude: this.longitude,
          latitude: this.latitude,
          radiusKm: 10,
          count: 20,
          tag: this.selectedTag || undefined
        })
        this.nearbyUsers = res || []
      } catch (e) {
        console.error(e)
      }
      this.loading = false
    },
    async doSearch() {
      if (!this.keyword && !this.selectedTag) {
        this.loadNearby()
        return
      }
      this.loading = true
      try {
        const res = await searchUsers({
          keyword: this.keyword,
          tag: this.selectedTag,
          pageNum: 1,
          pageSize: 20
        })
        this.nearbyUsers = res || []
      } catch (e) {}
      this.loading = false
    },
    selectTag(tag) {
      this.selectedTag = tag
      if (this.longitude) this.loadNearby()
      else this.doSearch()
    },
    goProfile(userId) {
      uni.navigateTo({ url: `/subpages/user-profile/user-profile?id=${userId}` })
    },
    formatDistance(km) {
      if (!km) return ''
      if (km < 1) return Math.round(km * 1000) + 'm'
      return km.toFixed(1) + 'km'
    }
  }
}
</script>

<style scoped>
.search-section { display: flex; align-items: center; margin-bottom: 20rpx; }
.search-input { flex: 1; background: #fff; border-radius: 40rpx; padding: 16rpx 24rpx; font-size: 26rpx; }
.search-btn { margin-left: 16rpx; padding: 16rpx 24rpx; font-size: 24rpx; }

.tag-scroll { white-space: nowrap; margin-bottom: 24rpx; }
.tag-item { display: inline-block; padding: 10rpx 24rpx; margin-right: 16rpx; border-radius: 30rpx; background: #fff; font-size: 24rpx; color: #666; }
.tag-item.active { background: #2b9939; color: #fff; }

.section-title { font-size: 30rpx; font-weight: 600; margin-bottom: 20rpx; }

.user-grid { display: flex; flex-wrap: wrap; gap: 16rpx; }
.user-card { width: calc(50% - 8rpx); text-align: center; padding: 24rpx 16rpx; }
.user-avatar { width: 140rpx; height: 140rpx; border-radius: 50%; margin-bottom: 12rpx; background: #eee; }
.user-name { font-size: 28rpx; font-weight: 500; display: block; margin-bottom: 6rpx; }
.user-distance { font-size: 22rpx; color: #999; display: block; margin-bottom: 8rpx; }
.user-tags { display: flex; justify-content: center; flex-wrap: wrap; gap: 8rpx; }
.mini-tag { font-size: 20rpx; background: #e8f5e9; color: #2b9939; padding: 4rpx 12rpx; border-radius: 20rpx; }

.empty { text-align: center; padding: 100rpx 0; color: #999; }
.load-more { text-align: center; padding: 30rpx; color: #999; }
</style>
