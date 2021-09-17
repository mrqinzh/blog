import request from '@/utils/request'

export function list() {
  return request({
    url: '/message/list',
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: '/message/add',
    method: 'post',
    data
  })
}