package com.pengfei.network.support;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.EncryptUtils;
import com.pengfei.network.config.BaseConfigKt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Unicode转码的工具类
 */
public class HttpUlicodeUtils {

    private HttpUlicodeUtils() {
    }

    /**
     * 中文转 unicode
     *
     * @param string
     * @return
     */
    public static String unicodeEncode(String string) {
        char[] utfBytes = string.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    /**
     * unicode 转中文
     *
     * @param string
     * @return
     */
    public static String unicodeDecode(String string) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(string);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
//            Integer.valueOf("", 16);
            string = string.replace(matcher.group(1), ch + "");
        }
        return string;
    }

    /**
     * 解析返回的data数据
     *
     * @param dataStr 未解密的响应数据
     * @return 解密后的数据String
     */
    @Nullable
    public static String decodeData(@Nullable String dataStr) {
        //java代码，无自动null判断，需要自行处理
        if (dataStr != null) {
            return new String(EncryptUtils.decryptBase64AES(
                    dataStr.getBytes(), BaseConfigKt.NET_CONFIG_APPKEY.getBytes(),
                    "AES/CBC/PKCS7Padding",   //解密的参数
                    "J#y9sJesv*5HmqLq".getBytes()   //解密
            ));
        } else {
            return null;
        }
    }
}
