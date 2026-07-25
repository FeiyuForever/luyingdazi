import { get, post } from '@/utils/request'

/** 创建活动 */
export function createActivity(data) {
  return post('/api/activity/create', data)
}

/** 活动详情 */
export function getActivityDetail(activityId) {
  return get(`/api/activity/${activityId}`)
}

/** 活动列表 */
export function getActivityList(pageNum, pageSize, city) {
  const params = { pageNum, pageSize }
  if (city) params.city = city
  return get('/api/activity/list', params)
}

/** 当前用户参加或发起的活动 */
export function getMyActivities(type) {
  return get('/api/activity/mine', { type })
}

export function isActivityJoined(activityId) {
  return get(`/api/activity/joined/${activityId}`)
}

/** 报名 */
export function joinActivity(activityId) {
  return post(`/api/activity/join/${activityId}`)
}

/** 取消报名 */
export function quitActivity(activityId) {
  return post(`/api/activity/quit/${activityId}`)
}
