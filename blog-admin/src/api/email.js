import request from '@/utils/request'

export function simpleEmail(data) {
  return request({
    url: '/email/simple',
    method: 'post',
    data
  })
}