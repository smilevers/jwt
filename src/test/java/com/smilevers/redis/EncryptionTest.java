package com.smilevers.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @Author: smile
 * @Date: 2020/2/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EncryptionTest {
    
    private String str = "https://mp.csdn.net/postlist";
    
    /**
     * spring自带base64加密解密工具类
     * @throws UnsupportedEncodingException
     */
    @Test
    public void base64Utils() throws UnsupportedEncodingException {
        // 普通加密
        String encode = Base64Utils.encodeToString("abc".getBytes());
        System.out.println(encode);
        String decode = Base64Utils.decodeFromString(encode).toString();
        System.out.println(decode);
        // url加密
        String s = Base64Utils.encodeToString(str.getBytes("utf-8"));
        System.out.println(s);
        String s1 = new String(Base64Utils.decodeFromUrlSafeString(s), "utf-8");
        System.out.println(s1);
    }
    
    /**
     * JDK1.8的base64加密解密工具类
     * @throws UnsupportedEncodingException
     */
    @Test
    public void jdkBase64() throws UnsupportedEncodingException {
        Base64.Encoder encoder = Base64.getEncoder();
        Base64.Decoder decoder = Base64.getDecoder();
        String encode = encoder.encodeToString(str.getBytes("UTF-8"));
        System.out.println(encode);
        String decode = new String(decoder.decode(encode),"UTF-8") ;
        System.out.println(decode);
        
    }
}
