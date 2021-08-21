import request from '@/utils/request'

export function list() {
  let url = '/tag/list'
  return request({
    url: url,
    method: 'get'
  })
}