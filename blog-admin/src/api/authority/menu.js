import request from '@/utils/request'

export function updateMenu(data) {
  return request({
    url: `/menu/update`,
    method: 'post',
    data
  })
}

export function getMenuById(id) {
  return request({
    url: `/menu/${id}`,
    method: 'get',
  })
}

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
