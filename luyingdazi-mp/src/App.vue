<script>
export default {
  onLaunch() {
    console.log('野趣搭子启动')
  }
}
</script>

<style>
/* 全局样式 */
page {
  background-color: #f5f5f5;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
  font-size: 28rpx;
  color: #333;
}

/* 通用类 */
.container {
  padding: 20rpx;
}

.flex-center {
  display: flex;
  align-items: center;
  justify-content: center;
}

.flex-between {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.btn-primary {
  background-color: #2b9939;
  color: #fff;
  border-radius: 40rpx;
  padding: 16rpx 40rpx;
  text-align: center;
  font-size: 28rpx;
}

.btn-outline {
  border: 1rpx solid #2b9939;
  color: #2b9939;
  border-radius: 40rpx;
  padding: 16rpx 40rpx;
  text-align: center;
  font-size: 28rpx;
}

.card {
  background-color: #fff;
  border-radius: 16rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
}
</style>
