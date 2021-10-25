import request from '@/utils/request'

export function getMenuList() {
  return request({
    url: `/menu/list`,
    method: 'get',
  })
}

export function addMenu(data) {
  return request({
    url: '/menu/add',
    method: 'post',
    data
  })
}
