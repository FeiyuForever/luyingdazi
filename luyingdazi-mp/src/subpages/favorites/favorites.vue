<template>
  <view class="container">
    <view class="empty" v-if="list.length === 0">
      <text class="empty-icon">⭐</text>
      <text>暂无收藏</text>
      <text class="empty-sub">去首页看看精彩动态吧</text>
    </view>
    <view v-else>
      <view class="post-card card" v-for="item in list" :key="item.id" @click="goDetail(item.id)">
        <text class="content">{{ item.content }}</text>
        <view class="meta flex-between">
          <text>{{ item.locationName }}</text>
          <text>{{ item.createdAt }}</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() { return { list: [] } },
  onLoad() {
    // TODO: 接入收藏接口
  },
  methods: {
    goDetail(id) { uni.navigateTo({ url: `/subpages/post-detail/post-detail?id=${id}` }) }
  }
}
</script>

<style scoped>
.empty { text-align: center; padding: 200rpx 0; }
.empty text { display: block; color: #999; font-size: 28rpx; margin-top: 12rpx; }
.empty-icon { font-size: 80rpx; }
.empty-sub { font-size: 24rpx; }
.content { font-size: 28rpx; display: block; margin-bottom: 12rpx; }
.meta { font-size: 22rpx; color: #999; }
</style>
