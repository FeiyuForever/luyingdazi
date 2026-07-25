<template>
  <view class="container">
    <!-- 内容输入 -->
    <textarea class="content-input" v-model="content" placeholder="分享你的露营故事..."
              maxlength="1000" :show-count="true" auto-height />

    <!-- 图片选择 -->
    <view class="image-section">
      <view class="image-grid">
        <view class="img-item" v-for="(img, idx) in images" :key="idx">
          <image :src="img" mode="aspectFill" class="preview-img" />
          <view class="img-del" @click="removeImage(idx)">×</view>
        </view>
        <view class="img-add" v-if="images.length < 9" @click="chooseImage">
          <text>+</text>
          <text class="img-add-text">{{ images.length }}/9</text>
        </view>
      </view>
    </view>

    <!-- 定位 -->
    <view class="location-section" @click="chooseLocation">
      <text>📍</text>
      <text class="location-text">{{ locationName || '添加位置' }}</text>
    </view>

    <!-- 发布按钮 -->
    <view class="btn-primary publish-btn" @click="handlePublish">发布</view>
  </view>
</template>

<script>
import { publishPost } from '@/api/post'
import { uploadImages } from '@/api/upload'

export default {
  data() {
    return {
      content: '',
      images: [],
      locationName: '',
      longitude: null,
      latitude: null
    }
  },
  methods: {
    chooseImage() {
      const remain = 9 - this.images.length
      uni.chooseImage({
        count: remain,
        sizeType: ['compressed'],
        sourceType: ['album', 'camera'],
        success: (res) => {
          // 这里先用本地路径，实际要上传到 OSS
          this.images = [...this.images, ...res.tempFilePaths]
        }
      })
    },
    removeImage(idx) {
      this.images.splice(idx, 1)
    },
    chooseLocation() {
      uni.chooseLocation({
        success: (res) => {
          this.locationName = res.name || res.address
          this.longitude = res.longitude
          this.latitude = res.latitude
        }
      })
    },
    async handlePublish() {
      if (!this.content && this.images.length === 0) {
        uni.showToast({ title: '请输入内容或选择图片', icon: 'none' })
        return
      }

      uni.showLoading({ title: '发布中...' })
      try {
        // 先上传图片到 OSS
        let imageUrls = []
        if (this.images.length > 0) {
          uni.showLoading({ title: '上传图片...' })
          imageUrls = await uploadImages(this.images)
        }

        // 发布动态
        uni.showLoading({ title: '发布中...' })
        await publishPost({
          content: this.content,
          images: imageUrls,
          locationName: this.locationName,
          longitude: this.longitude,
          latitude: this.latitude
        })
        uni.hideLoading()
        uni.showToast({ title: '发布成功', icon: 'success' })
        setTimeout(() => uni.navigateBack(), 1500)
      } catch (e) {
        uni.hideLoading()
      }
    }
  }
}
</script>

<style scoped>
.content-input { width: 100%; min-height: 300rpx; background: #fff; border-radius: 16rpx; padding: 24rpx; font-size: 30rpx; line-height: 1.6; box-sizing: border-box; }

.image-section { margin-top: 24rpx; }
.image-grid { display: flex; flex-wrap: wrap; gap: 12rpx; }
.img-item { position: relative; width: 210rpx; height: 210rpx; }
.preview-img { width: 100%; height: 100%; border-radius: 12rpx; }
.img-del { position: absolute; top: -10rpx; right: -10rpx; width: 40rpx; height: 40rpx; background: rgba(0,0,0,0.6); color: #fff; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 28rpx; }
.img-add { width: 210rpx; height: 210rpx; border: 2rpx dashed #ccc; border-radius: 12rpx; display: flex; flex-direction: column; align-items: center; justify-content: center; color: #999; }
.img-add text:first-child { font-size: 60rpx; }
.img-add-text { font-size: 22rpx; margin-top: 8rpx; }

.location-section { display: flex; align-items: center; background: #fff; border-radius: 12rpx; padding: 24rpx; margin-top: 24rpx; }
.location-text { margin-left: 12rpx; font-size: 28rpx; color: #666; }

.publish-btn { margin-top: 40rpx; text-align: center; padding: 24rpx; font-size: 32rpx; }
</style>
