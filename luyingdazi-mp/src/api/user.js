import { get, post, put } from '@/utils/request'

/** 微信登录 */
export function wxLogin(data) {
  return post('/api/user/wx-login', data)
}

/** 获取当前用户信息 */
export function getUserInfo() {
  return get('/api/user/info')
}

/** 获取指定用户主页 */
export function getUserProfile(userId) {
  return get(`/api/user/profile/${userId}`)
}

/** 更新个人资料 */
export function updateProfile(data) {
  return put('/api/user/profile', data)
}

/** 上报位置 */
export function reportLocation(data) {
  return post('/api/user/location', data)
}
