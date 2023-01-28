import { getConfigByKeys } from '@/api/authority/sys-config'

/** 处理configKey返回值,转化为map类型 */
export function initSystemConfigs(data) {
    let configList = [];
    return getConfigByKeys(data).then(resp => {
        configList = resp.data;
        const result = new Map;
        for (let i in configList) {
            result.set(configList[i].configKey, configList[i].configValue);
        }
        return result;
    });
}