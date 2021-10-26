import request from '@/utils/request'

export function getRoleList(data) {
  return request({
    url: '/role/page',
    method: 'get',
    params: data
  })
}