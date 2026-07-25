import { get, post } from '@/utils/request'

export function toggleFavorite(postId) {
  return post(`/api/favorite/toggle/${postId}`)
}

export function checkFavorite(postId) {
  return get(`/api/favorite/check/${postId}`)
}

export function getFavorites(pageNum = 1, pageSize = 20) {
  return get('/api/favorite/list', { pageNum, pageSize })
}
