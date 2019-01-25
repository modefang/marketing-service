package com.yun.demo.springbootdemo.util;

public class StringUtils {

    public static boolean isEmpty(String... args) {
        for (String s : args) {
            if (s == null || s.trim().length() == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotEmpty(String... args) {
        for (String s : args) {
            if (s == null || s.trim().length() == 0) {
                return false;
            }
        }
        return true;
    }

}
