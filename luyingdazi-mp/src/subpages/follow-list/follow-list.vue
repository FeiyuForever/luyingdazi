<template>
  <view class="container">
    <view class="user-item card" v-for="user in list" :key="user.id" @click="goProfile(user.id)">
      <image class="avatar" :src="user.avatar || '/static/default-avatar.svg'" mode="aspectFill" />
      <view class="user-info">
        <text class="name">{{ user.nickname }}</text>
        <text class="bio">{{ user.bio || '' }}</text>
      </view>
    </view>
    <view class="empty" v-if="!loading && list.length === 0">
      <text>{{ type === 'follow' ? '暂未关注任何人' : '暂无粉丝' }}</text>
    </view>
  </view>
</template>

<script>
import { get } from '@/utils/request'

export default {
  data() {
    return { list: [], type: 'follow', userId: null, loading: false }
  },
  onLoad(options) {
    this.type = options.type || 'follow'
    this.userId = options.userId
    uni.setNavigationBarTitle({ title: this.type === 'follow' ? '关注列表' : '粉丝列表' })
    this.loadList()
  },
  methods: {
    async loadList() {
      this.loading = true
      try {
        const res = await get(`/api/follow/list/${this.userId}`, { type: this.type })
        this.list = res || []
      } catch (e) {}
      this.loading = false
    },
    goProfile(id) {
      uni.navigateTo({ url: `/subpages/user-profile/user-profile?id=${id}` })
    }
  }
}
</script>

<style scoped>
.user-item { display: flex; align-items: center; padding: 20rpx 24rpx; margin-bottom: 2rpx; }
.avatar { width: 88rpx; height: 88rpx; border-radius: 50%; margin-right: 20rpx; background: #eee; }
.user-info { flex: 1; }
.name { font-size: 30rpx; font-weight: 500; display: block; }
.bio { font-size: 24rpx; color: #999; margin-top: 6rpx; display: block; }
.empty { text-align: center; padding: 100rpx 0; color: #999; }
</style>
