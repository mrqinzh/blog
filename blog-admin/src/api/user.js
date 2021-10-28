import request from '@/utils/request'

export function add(data) {
  return request({
    url: '/user/add',
    method: 'post',
    data
  })
}

export function getUserById(id) {
  let url = `/user/${id}`
  return request({
    url: url,
    method: 'get',
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
    url: '/login',
    method: 'post',
    transformRequest: [
      function (data) {
        let ret = ''
        for (let it in data) {
          ret +=
            encodeURIComponent(it) +
            '=' +
            encodeURIComponent(data[it]) +
            '&'
        }
        return ret
      }
    ],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
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
    url: '/logout',
    method: 'post',
  })
}