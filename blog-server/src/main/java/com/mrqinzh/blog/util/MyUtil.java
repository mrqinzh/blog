package com.mrqinzh.blog.util;

import java.util.Random;

public class MyUtil {

    /**
     * 获取指定范围内的随机数
     * @param max
     * @return
     */
    public static int randomInt(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

}
