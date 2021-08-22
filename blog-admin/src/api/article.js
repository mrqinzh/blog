import request from '@/utils/request'

export function add(data) {
  return request({
    url: '/article/add',
    method: 'post',
    data
  })
}

export function getById(articleId) {
  let url = `/article/${articleId}`;
  return request({
    url: url,
    method: 'get'
  })
}

export function list(currentPage, pageSize, condition) {
  let url = `/article/list?currentPage=${currentPage}&pageSize=${pageSize}&condition=${condition}`;
  return request({
    url: url,
    method: 'get'
  })
}