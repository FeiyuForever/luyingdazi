<template>
  <view class="container">
    <view class="form card">
      <view class="form-item" @click="chooseAvatar">
        <text class="label">头像</text>
        <image class="avatar-preview" :src="form.avatar || '/static/default-avatar.png'" mode="aspectFill" />
      </view>
      <view class="form-item">
        <text class="label">昵称</text>
        <input v-model="form.nickname" placeholder="取个好听的名字" maxlength="20" />
      </view>
      <view class="form-item">
        <text class="label">性别</text>
        <view class="gender-row">
          <view class="gender-btn" :class="{ active: form.gender === 1 }" @click="form.gender = 1">男</view>
          <view class="gender-btn" :class="{ active: form.gender === 2 }" @click="form.gender = 2">女</view>
        </view>
      </view>
      <view class="form-item">
        <text class="label">城市</text>
        <input v-model="form.city" placeholder="你在哪个城市" />
      </view>
      <view class="form-item">
        <text class="label">个人简介</text>
        <textarea v-model="form.bio" placeholder="介绍一下自己..." maxlength="200" auto-height />
      </view>
      <view class="form-item">
        <text class="label">露营经验</text>
        <view class="exp-row">
          <view class="exp-btn" :class="{ active: form.campingYears === i }"
                v-for="(txt, i) in expList" :key="i" @click="form.campingYears = i">{{ txt }}</view>
        </view>
      </view>
      <view class="form-item">
        <text class="label">露营标签（选择你喜欢的）</text>
        <view class="tag-row">
          <view class="tag-btn" :class="{ active: selectedTags.includes(t) }"
                v-for="t in allTags" :key="t" @click="toggleTag(t)">{{ t }}</view>
        </view>
      </view>
    </view>
    <view class="btn-primary save-btn" @click="handleSave">保存</view>
  </view>
</template>

<script>
import { updateProfile } from '@/api/user'

export default {
  data() {
    return {
      form: { nickname: '', avatar: '', gender: 0, city: '', bio: '', campingYears: 0 },
      selectedTags: [],
      expList: ['新手', '一年内', '1-3年', '3年+'],
      allTags: ['自驾露营', '徒步露营', '烧烤', '星空摄影', '亲子', '钓鱼', '骑行', '登山', '溯溪', '漂流']
    }
  },
  onLoad() {
    const info = uni.getStorageSync('userInfo')
    if (info) {
      this.form = { ...this.form, ...info }
      this.selectedTags = info.tags || []
    }
  },
  methods: {
    chooseAvatar() {
      uni.chooseImage({
        count: 1, sizeType: ['compressed'],
        success: (res) => { this.form.avatar = res.tempFilePaths[0] }
      })
    },
    toggleTag(tag) {
      const idx = this.selectedTags.indexOf(tag)
      if (idx >= 0) this.selectedTags.splice(idx, 1)
      else if (this.selectedTags.length < 5) this.selectedTags.push(tag)
      else uni.showToast({ title: '最多选5个', icon: 'none' })
    },
    async handleSave() {
      if (!this.form.nickname) return uni.showToast({ title: '请填写昵称', icon: 'none' })
      uni.showLoading({ title: '保存中...' })
      try {
        await updateProfile({ ...this.form, tags: this.selectedTags })
        uni.setStorageSync('userInfo', { ...this.form, tags: this.selectedTags })
        uni.hideLoading()
        uni.showToast({ title: '保存成功', icon: 'success' })
        setTimeout(() => uni.navigateBack(), 1000)
      } catch (e) { uni.hideLoading() }
    }
  }
}
</script>

<style scoped>
.form-item { padding: 24rpx 0; border-bottom: 1rpx solid #f5f5f5; }
.label { font-size: 26rpx; color: #999; display: block; margin-bottom: 12rpx; }
.avatar-preview { width: 120rpx; height: 120rpx; border-radius: 50%; background: #eee; }
input, textarea { font-size: 28rpx; width: 100%; }

.gender-row, .exp-row { display: flex; gap: 16rpx; }
.gender-btn, .exp-btn { padding: 12rpx 28rpx; border-radius: 30rpx; background: #f5f5f5; font-size: 26rpx; }
.gender-btn.active, .exp-btn.active { background: #2b9939; color: #fff; }

.tag-row { display: flex; flex-wrap: wrap; gap: 12rpx; }
.tag-btn { padding: 10rpx 20rpx; border-radius: 30rpx; background: #f5f5f5; font-size: 24rpx; }
.tag-btn.active { background: #e8f5e9; color: #2b9939; border: 1rpx solid #2b9939; }

.save-btn { margin-top: 40rpx; text-align: center; padding: 24rpx; font-size: 32rpx; }
</style>
