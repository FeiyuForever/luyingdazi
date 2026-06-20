import { get, post } from '@/utils/request'

/** 会话列表 */
export function getSessionList() {
  return get('/api/chat/sessions')
}

/** 聊天记录 */
export function getMessages(sessionId, pageNum, pageSize) {
  return get(`/api/chat/messages/${sessionId}`, { pageNum, pageSize })
}

/** 发送消息 */
export function sendMessage(data) {
  return post('/api/chat/send', data)
}

/** 标记已读 */
export function markRead(sessionId) {
  return post(`/api/chat/read/${sessionId}`)
}
