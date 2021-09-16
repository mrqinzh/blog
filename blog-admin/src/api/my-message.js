import request from '@/utils/request'

export function list() {
  return request({
    url: '/message/list',
    method: 'get'
  })
}