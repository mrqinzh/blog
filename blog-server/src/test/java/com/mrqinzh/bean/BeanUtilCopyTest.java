package com.mrqinzh.bean;

import com.mrqinzh.blog.model.entity.User;
import com.mrqinzh.blog.model.vo.req.UserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class BeanUtilCopyTest {

    @Test
    public void test1() {
        User user = new User();
        user.setUserPwd("1");
        user.setLoginLastTime(new Date());
        user.setUserAvatar("1");
        user.setUserNickname("mrqinzh");
        user.setUserName("admin");
        user.setUserEmail("1");

        UserVO userVO = new UserVO();

        BeanUtils.copyProperties(user, userVO);

        System.out.println("userVO = " + userVO);
    }

}
