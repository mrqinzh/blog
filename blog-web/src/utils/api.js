import axios from 'axios'

// let base = 'http://47.108.209.62:9090';
// let base = 'http://localhost:9090';

// export const base = 'http://47.108.209.62:9090';
export const base = 'http://localhost:9090';

export const postRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: JSON.stringify(params),
    headers: {
      "Content-Type": "application/json; charset=UTF-8",
      "token": localStorage.getItem("token"),
    }
  });
}
export const uploadFileRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    headers: {
      'Content-Type': 'multipart/form-data',
      'token': localStorage.getItem("token"),
    }
  });
}
export const putRequest = (url, params) => {
  return axios({
    method: 'put',
    url: `${base}${url}`,
    data: params,
    transformRequest: [function (data) {
      let ret = ''
      for (let it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      return ret
    }],
    headers: {
      "Content-Type": "application/json; charset=UTF-8",
      "token": localStorage.getItem("token"),
    }
  });
}
export const deleteRequest = (url) => {
  return axios({
    method: 'delete',
    url: `${base}${url}`,
    headers: {
      "Content-Type": "application/json; charset=UTF-8",
      "token": localStorage.getItem("token"),
    }
  });
}
export const getRequest = (url,params) => {
  return axios({
    method: 'get',
    data: JSON.stringify(params),
    transformRequest: [function (data) {
      let ret = ''
      for (let it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      return ret
    }],
    headers: {
      "Content-Type": "application/json; charset=UTF-8",
      "token": localStorage.getItem("token"),
    },
    url: `${base}${url}`
  });
}
