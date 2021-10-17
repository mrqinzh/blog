import request from '@/utils/request'

export function getAllList(data) {
// export function getAllList(currentPage, pageSize, condition, searchParam) {
  // let url = `/comment/list?currentPage=${currentPage}&pageSize=${pageSize}&condition=${condition}`;
  return request({
    url: `/comment/list`,
    method: 'get',
    params: data
  })
}

export function getByArticleIdOrUserId(idType, id) {
  let url = `/comment/${idType}/${id}`;
  return request({
    url: url,
    method: 'get',
  })
}

export function add(data) {
  return request({
    url: '/comment/add',
    method: 'post',
    data
  })
}

export function getMessageList() {
  return request({
    url: '/comment/message-list',
    method: 'get',
  })
}