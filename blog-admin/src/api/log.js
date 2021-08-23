import request from '@/utils/request'

export function list(currentPage, pageSize, condition) {
  let url = `/login-log/list?currentPage=${currentPage}&pageSize=${pageSize}&condition=${condition}`;
  return request({
    url: url,
    method: 'get'
  })
}