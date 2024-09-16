package com.metoo.monitor.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.metoo.monitor.core.dto.LicenseDto;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;

@Component
public class AesEncryptUtils {

    //可配置到Constant中，并读取配置文件注入,16位,自定义
    private static final String KEY = "@NPzwDvPmCJvpYuE";

    //参数分别代表 算法名称/加密模式/数据填充方式
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * 加密
     * @param content 加密的字符串
     * @param encryptKey key值
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);/*提供加密的方式：DES*/
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        byte[] b = cipher.doFinal(content.getBytes("UTF-8"));
        // 采用base64算法进行转码,避免出现中文乱码
        return Base64.encodeBase64String(b);

    }

    /**
     * 解密
     * @param encryptStr 解密的字符串
     * @param decryptKey 解密的key值
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptStr, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        // 采用base64算法进行转码,避免出现中文乱码
        byte[] encryptBytes = Base64.decodeBase64(encryptStr.getBytes("UTF-8"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    public String encrypt(String content) throws Exception {
        return encrypt(content, KEY);
    }
    public String decrypt(String encryptStr) throws Exception {
        return decrypt(encryptStr, KEY);
    }

    public static void main(String[] args) throws Exception {
        Map map=new HashMap<String,String>();
//        map.put("expireTime","1964053510");
        map.put("systemSN","SYSTEM");
        LicenseDto dto = new LicenseDto();
        dto.setStartTime(1704791990000L);
        dto.setEndTime(1707470390000L);
        dto.setSystemSN("Y3HaRlRtspphK9W8v/4VUr1l4728jYrkAURR5cGpUTWVwy+8fD5741NkqLnZKEXJ");
//        dto.setType(0);
        dto.setLicenseVersion("1.0");
        dto.setType("试用版");
        dto.setArea("南昌");
//        dto.setLicenseFireWall(0);
//        dto.setLicenseRouter(0);
//        dto.setLicenseHost(0);
//        dto.setLicenseUe(0);
        String content = JSONObject.toJSONString(dto);
        System.out.println("加密前：" + content);
//
        String encrypt = encrypt(content, KEY);
        System.out.println("加密后：" + encrypt);

        encrypt = "a4PmtvfgqseNT1AZs4oyUUpQfILZd1vY+FXxFtnVzDzfKhesbleYPfLMjXF9Pb5w7lwyAMNv20YZdkjx3jexzp3ncYORypW2p8i8ehETywnHRbmqPpOr2VMN3VntbufDGzDHJQcCXq+S7JH2jb+rjt+ttOA6fCPCl+3Cq+HUXLrMzMe2JhxUcapUUJVjgcfer44+ZhJrYwxE2teFoMfG+VzPlNf8gJ5kHgtPOh256Ts0z+IlQTH+UX6LA7Q7ezInAVnYyjmU5H65cu77GdvgkmqXtnoK02M99Fgxw50aEYfH45zHxsHkHBgenHfD9iVbv7IOPKNflYe2jgSiATD8aQ==";

        String decrypt = decrypt(encrypt, KEY);
        System.out.println("解密后：" + decrypt);

    }
//    public static void main(String[] args) throws Exception {
//        Map map=new HashMap<String,String>();
////        map.put("expireTime","1964053510");
//        map.put("systemSN","SYSTEM");
//        LicenseDto dto = new LicenseDto();
//        dto.setStartTime(1704791990000L);
//        dto.setEndTime(1707470390000L);
//        dto.setSystemSN("Y3HaRlRtspphK9W8v/4VUr1l4728jYrkAURR5cGpUTWVwy+8fD5741NkqLnZKEXJ");
////        dto.setType(0);
//        dto.setLicenseVersion("1.0");
//        dto.setType("0");
//        dto.setLicenseFireWall(0);
//        dto.setLicenseRouter(0);
//        dto.setLicenseHost(0);
//        dto.setLicenseUe(0);
//        String content = JSONObject.toJSONString(dto);
//        System.out.println("加密前：" + content);
//
//        String encrypt = encrypt(content, KEY);
//        System.out.println("加密后：" + encrypt);
//
//        String decrypt = decrypt(encrypt, KEY);
//        System.out.println("解密后：" + decrypt);
//
//    }


//    public static void main(String[] args) {
//        // 比较时间戳
//        String endTimeStamp = "1647936175";
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        long currentTime = calendar.getTimeInMillis();
//        long timeStampSec = currentTime / 1000;// 13位时间戳（单位毫秒）转换为10位字符串（单位秒）
//        String timestamp = String.format("%010d", timeStampSec);// 当前时间
//        System.out.println(Long.valueOf(endTimeStamp).compareTo(Long.valueOf(timestamp))>0);
//    }


}
