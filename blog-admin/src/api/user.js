import request from '@/utils/request'

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

// 访问到上面后端部分的登出方法
export function logout() {
  return request({
    url: '/user/logout',
    method: 'post',
    params:{
      methodName:'logout'
    }
  })
}