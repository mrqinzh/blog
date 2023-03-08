import request from '@/utils/request'

export function getSysConfig(data) {
    return request({
        url: '/sys/config',
        method: 'get',
        params: data,
    })
}

export function getConfigByKeys(data) {
    let param = {
        configKeys: data
    }
    return request({
        url: '/sys/config/byKeys',
        method: 'get',
        params: param,
    })
}

export function updateConfig(data) {
    return request({
        url: '/sys/config/setting',
        method: 'post',
        data
    })
}


export var configKeyMap = new Map ([
    ['MY_PRIMARY_DOMAIN', '主域名'],
    ['IMG_DOMAIN', '图片域名'],
    ['PROJECT_START_TIME', '项目开始时间'],
    ['PROJECT_FIRST_USE_TIME', '项目首次使用时间'],
    ['RECORD_CODE', '公安部备案号'],
    ['ICP', 'ICP备案号'],
    ['DEFAULT_AVATAR_LINK', '默认头像链接'],
])

export var keys = {
    configKeys: 'MY_PRIMARY_DOMAIN, IMG_DOMAIN, PROJECT_START_TIME, PROJECT_FIRST_USE_TIME'
}