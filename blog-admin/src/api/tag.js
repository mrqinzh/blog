import request from '@/utils/request'

export function page(currentPage, pageSize, condition) {
  let url = `/tag/page?currentPage=${currentPage}&pageSize=${pageSize}&condition=${condition}`;
  return request({
    url: url,
    method: 'get'
  })
}

export function list() {
  return request({
    url: '/tag/list',
    method: 'get'
  })
}

export function getById(tagId) {
  let url = `/tag/${tagId}`;
  return request({
    url: url,
    method: 'get'
  })
}
