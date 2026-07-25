/**
 * HTTP 请求封装
 */

const BASE_URL = 'https://api.luyingdazi.xyz' // 生产环境

/**
 * 统一请求方法
 */
function request(options) {
  return new Promise((resolve, reject) => {
    const token = uni.getStorageSync('token')
    const requestData = {}
    Object.keys(options.data || {}).forEach((key) => {
      if (options.data[key] !== undefined) requestData[key] = options.data[key]
    })
    
    uni.request({
      url: BASE_URL + options.url,
      method: options.method || 'GET',
      data: requestData,
      header: {
        'Content-Type': 'application/json',
        'Authorization': token ? `Bearer ${token}` : '',
        ...options.header
      },
      success: (res) => {
        const data = res.data
        if (data.code === 200) {
          resolve(data.data)
        } else if (data.code === 1001 || data.code === 1003) {
          // Token 过期，跳转登录
          uni.removeStorageSync('token')
          uni.removeStorageSync('userInfo')
          uni.reLaunch({ url: '/pages/mine/mine' })
          reject(data)
        } else {
          uni.showToast({ title: data.msg || '请求失败', icon: 'none' })
          reject(data)
        }
      },
      fail: (err) => {
        uni.showToast({ title: '网络异常', icon: 'none' })
        reject(err)
      }
    })
  })
}

export function get(url, data) {
  return request({ url, method: 'GET', data })
}

export function post(url, data) {
  return request({ url, method: 'POST', data })
}

export function put(url, data) {
  return request({ url, method: 'PUT', data })
}

export function del(url, data) {
  return request({ url, method: 'DELETE', data })
}

export default request
