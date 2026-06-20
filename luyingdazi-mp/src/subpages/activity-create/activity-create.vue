<template>
  <view class="container">
    <view class="form card">
      <view class="form-item">
        <text class="label">活动标题</text>
        <input v-model="form.title" placeholder="例：周末白马湖露营约伴" />
      </view>
      <view class="form-item">
        <text class="label">活动描述</text>
        <textarea v-model="form.description" placeholder="介绍一下这次活动..." auto-height />
      </view>
      <view class="form-item" @click="chooseLocation">
        <text class="label">活动地点</text>
        <text class="value">{{ form.locationName || '点击选择位置' }}</text>
      </view>
      <view class="form-item" @click="pickStartTime">
        <text class="label">开始时间</text>
        <text class="value">{{ form.startTime || '选择时间' }}</text>
      </view>
      <view class="form-item" @click="pickEndTime">
        <text class="label">结束时间</text>
        <text class="value">{{ form.endTime || '选择时间' }}</text>
      </view>
      <view class="form-item">
        <text class="label">人数上限（0为不限）</text>
        <input v-model="form.maxMembers" type="number" placeholder="0" />
      </view>
      <view class="form-item">
        <text class="label">费用说明</text>
        <input v-model="form.feeDesc" placeholder="免费 / AA / 人均xx元" />
      </view>
      <view class="form-item">
        <text class="label">参与要求（选填）</text>
        <input v-model="form.requirement" placeholder="如：有帐篷优先" />
      </view>
    </view>
    <view class="btn-primary submit-btn" @click="handleSubmit">发布活动</view>
  </view>
</template>

<script>
import { createActivity } from '@/api/activity'

export default {
  data() {
    return {
      form: { title: '', description: '', locationName: '', longitude: null, latitude: null, startTime: '', endTime: '', maxMembers: 0, feeDesc: '免费', requirement: '' }
    }
  },
  methods: {
    chooseLocation() {
      uni.chooseLocation({
        success: (res) => {
          this.form.locationName = res.name || res.address
          this.form.longitude = res.longitude
          this.form.latitude = res.latitude
        }
      })
    },
    pickStartTime() {
      uni.showToast({ title: '请使用日期选择器', icon: 'none' })
      // 简化：实际用 uni-datetime-picker 组件
    },
    pickEndTime() {
      uni.showToast({ title: '请使用日期选择器', icon: 'none' })
    },
    async handleSubmit() {
      if (!this.form.title) return uni.showToast({ title: '请填写标题', icon: 'none' })
      if (!this.form.locationName) return uni.showToast({ title: '请选择地点', icon: 'none' })

      uni.showLoading({ title: '发布中...' })
      try {
        await createActivity(this.form)
        uni.hideLoading()
        uni.showToast({ title: '发布成功', icon: 'success' })
        setTimeout(() => uni.navigateBack(), 1500)
      } catch (e) { uni.hideLoading() }
    }
  }
}
</script>

<style scoped>
.form-item { padding: 24rpx 0; border-bottom: 1rpx solid #f5f5f5; }
.form-item:last-child { border: none; }
.label { font-size: 26rpx; color: #999; display: block; margin-bottom: 12rpx; }
.value { font-size: 28rpx; color: #666; }
input, textarea { font-size: 28rpx; width: 100%; }
.submit-btn { margin-top: 40rpx; text-align: center; padding: 24rpx; font-size: 32rpx; }
</style>
