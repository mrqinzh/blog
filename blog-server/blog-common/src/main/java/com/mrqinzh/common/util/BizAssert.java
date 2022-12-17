package com.mrqinzh.common.util;

import com.mrqinzh.common.exception.BizException;
import org.apache.logging.log4j.util.Strings;

public class BizAssert {

    public static void isFalse(String msg, boolean expression) {
        isTrue(msg, !expression);
    }

    public static void isTrue(boolean expression, String msg) {
        if (!expression) {
            throw new BizException(msg);
        }
    }

    public static void isTrue(String msg, boolean expression) {
        if (!expression) {
            throw new BizException(msg);
        }
    }

    public static void isNull(String msg, Object obj) {
        if (obj != null) throw new BizException(msg);
    }

    public static void notNull(Object obj, String msg) {
        if (obj == null) throw new BizException(msg);
    }

    public static void isBlank(String msg, String str) {
        if (Strings.isNotBlank(str)) throw new BizException(msg);
    }
    public static void isBlank(String msg, String... str) {
        for (String s : str) {
            isBlank(msg, s);
        }
    }

    public static void isNotBlank(String msg, String str) {
        if (Strings.isBlank(str)) throw new BizException(msg);
    }
    public static void isNotBlank(String msg, String... str) {
        for (String s : str) {
            isNotBlank(msg, s);
        }
    }

}
