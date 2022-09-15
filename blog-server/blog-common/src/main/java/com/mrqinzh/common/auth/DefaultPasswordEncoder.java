package com.mrqinzh.common.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 加密解密
 */
@Component
public class DefaultPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    /**
     * 比较密码
     * @param rawPassword 前端传入
     * @param encodedPassword 数据库查询
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }

}
