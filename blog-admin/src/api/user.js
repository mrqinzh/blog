import request from '@/utils/request'

export function add(data) {
  return request({
    url: '/user/add',
    method: 'post',
    data
  })
}

export function list(currentPage, pageSize, condition) {
  let url = `/user/list?currentPage=${currentPage}&pageSize=${pageSize}&condition=${condition}`;
  return request({
    url: url,
    method: 'get'
  })
}

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post',
    params:{
      methodName:'logout'
    }
  })
}