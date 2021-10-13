package com.mrqinzh.blog.util;

import java.util.Random;

public class MyUtil {

    /**
     * 将 content 中的 HTML 标签过滤
     * @param content HTML
     * @return java.lang.String
     */
    public static String stripHtml(String content) {
        content = content.replaceAll("<p .*?>", "");
        content = content.replaceAll("<br\\s*/?>", "");
        content = content.replaceAll("\\<.*?>", "");
        return content;
    }

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
