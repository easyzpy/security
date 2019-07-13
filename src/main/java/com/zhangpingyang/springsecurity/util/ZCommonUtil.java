package com.zhangpingyang.springsecurity.util;

import java.util.regex.Pattern;

public class ZCommonUtil {
    public static boolean isMatch(String regex, String string) {
        return Pattern.compile(regex).matcher(string).matches();
    }
}
