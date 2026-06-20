import { get, post, del } from '@/utils/request'

/** 发布动态 */
export function publishPost(data) {
  return post('/api/post/publish', data)
}

/** 动态详情 */
export function getPostDetail(postId) {
  return get(`/api/post/${postId}`)
}

/** 首页动态流 */
export function getPostFeed(pageNum, pageSize, keyword) {
  const params = { pageNum, pageSize }
  if (keyword) params.keyword = keyword
  return get('/api/post/feed', params)
}

/** 用户动态列表 */
export function getUserPosts(userId, pageNum, pageSize) {
  return get(`/api/post/user/${userId}`, { pageNum, pageSize })
}

/** 点赞/取消点赞 */
export function toggleLike(postId) {
  return post(`/api/post/like/${postId}`)
}

/** 删除动态 */
export function deletePost(postId) {
  return del(`/api/post/${postId}`)
}
