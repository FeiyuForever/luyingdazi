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
      <picker mode="date" :start="today" :value="startDate" @change="onStartDateChange">
        <view class="form-item">
          <text class="label">开始日期</text>
          <text class="value">{{ startDate || '选择日期' }}</text>
        </view>
      </picker>
      <picker mode="time" :value="startClock" @change="onStartClockChange">
        <view class="form-item">
          <text class="label">开始时间</text>
          <text class="value">{{ startClock || '选择时间' }}</text>
        </view>
      </picker>
      <picker mode="date" :start="startDate || today" :value="endDate" @change="onEndDateChange">
        <view class="form-item">
          <text class="label">结束日期</text>
          <text class="value">{{ endDate || '选择日期' }}</text>
        </view>
      </picker>
      <picker mode="time" :value="endClock" @change="onEndClockChange">
        <view class="form-item">
          <text class="label">结束时间</text>
          <text class="value">{{ endClock || '选择时间' }}</text>
        </view>
      </picker>
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
      form: { title: '', description: '', locationName: '', longitude: null, latitude: null, startTime: '', endTime: '', maxMembers: 0, feeDesc: '免费', requirement: '' },
      today: '',
      startDate: '',
      startClock: '',
      endDate: '',
      endClock: ''
    }
  },
  onLoad() {
    const now = new Date()
    this.today = this.formatDate(now)
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
    onStartDateChange(e) {
      this.startDate = e.detail.value
      if (this.endDate && this.endDate < this.startDate) this.endDate = this.startDate
      this.syncTimes()
    },
    onStartClockChange(e) {
      this.startClock = e.detail.value
      this.syncTimes()
    },
    onEndDateChange(e) {
      this.endDate = e.detail.value
      this.syncTimes()
    },
    onEndClockChange(e) {
      this.endClock = e.detail.value
      this.syncTimes()
    },
    syncTimes() {
      this.form.startTime = this.startDate && this.startClock
        ? `${this.startDate}T${this.startClock}:00`
        : ''
      this.form.endTime = this.endDate && this.endClock
        ? `${this.endDate}T${this.endClock}:00`
        : ''
    },
    formatDate(date) {
      const pad = (n) => String(n).padStart(2, '0')
      return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}`
    },
    async handleSubmit() {
      if (!this.form.title) return uni.showToast({ title: '请填写标题', icon: 'none' })
      if (!this.form.locationName) return uni.showToast({ title: '请选择地点', icon: 'none' })
      if (!this.form.startTime || !this.form.endTime) {
        return uni.showToast({ title: '请选择完整的开始和结束时间', icon: 'none' })
      }
      if (new Date(this.form.endTime) <= new Date(this.form.startTime)) {
        return uni.showToast({ title: '结束时间必须晚于开始时间', icon: 'none' })
      }

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
