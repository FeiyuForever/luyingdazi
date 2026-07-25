const BASE_URL = 'https://api.luyingdazi.xyz'

/**
 * 上传单张图片到 OSS
 * @param {string} filePath 本地文件路径
 * @returns {Promise<string>} 图片URL
 */
export function uploadImage(filePath) {
  return new Promise((resolve, reject) => {
    const token = uni.getStorageSync('token')
    uni.uploadFile({
      url: BASE_URL + '/api/upload/image',
      filePath: filePath,
      name: 'file',
      header: {
        'Authorization': token ? `Bearer ${token}` : ''
      },
      success: (res) => {
        if (res.statusCode === 200) {
          const data = JSON.parse(res.data)
          if (data.code === 200) {
            resolve(data.data)
          } else {
            uni.showToast({ title: data.msg || '上传失败', icon: 'none' })
            reject(data)
          }
        } else {
          reject(res)
        }
      },
      fail: (err) => {
        uni.showToast({ title: '上传失败', icon: 'none' })
        reject(err)
      }
    })
  })
}

/**
 * 批量上传图片
 * @param {string[]} filePaths 本地文件路径数组
 * @returns {Promise<string[]>} 图片URL数组
 */
export async function uploadImages(filePaths) {
  const urls = []
  for (const path of filePaths) {
    const url = await uploadImage(path)
    urls.push(url)
  }
  return urls
}
