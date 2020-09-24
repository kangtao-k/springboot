package com.md.common;


import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.security.MessageDigest;

@Configuration
public class MD5 {

    /**
     * 对字符串md5加密(小写字母+数字)
     *
     * @param str 传入要加密的字符串
     * @return  MD5加密后的字符串
     */

    public static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
