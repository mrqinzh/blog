package com.mrqinzh.domain.config;

public enum ConfigKey {

    MY_PRIMARY_DOMAIN("主域名"),
    ;


    private String desc;
    ConfigKey(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }

    public static boolean validConfigKey(String key) {
        ConfigKey[] keys = ConfigKey.values();
        for (ConfigKey configKey : keys) {
            if (configKey.name().equals(key)) {
                return true;
            }
        }
        return false;
    }

}
