import request from '@/utils/request'

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