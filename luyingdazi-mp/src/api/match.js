import { get, post } from '@/utils/request'

/** 附近的人 */
export function getNearbyUsers(data) {
  return post('/api/match/nearby', data)
}

/** 智能推荐 */
export function getRecommendUsers() {
  return get('/api/match/recommend')
}

/** 搜索用户 */
export function searchUsers(params) {
  return get('/api/match/search', params)
}
