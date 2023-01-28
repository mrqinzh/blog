import request from '@/utils/request'

export function delImg(fileName) {
  return request({
    url: `/file/delete/${fileName}`,
    method: 'delete'
  })
}

export function uploadFileRequest (data) {
  return request({
    method: 'post',
    url: '/file/add',
    data: data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
}