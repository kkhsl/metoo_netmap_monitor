package com.metoo.monitor.core.utils.aes;

import com.alibaba.fastjson.JSONObject;
import com.metoo.monitor.core.dto.LicenseDto;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HKK
 * @version 1.0
 * @date 2024-01-11 11:06
 */
@Component
public class Aes {

    private static final String PWD = "ASDFGHJKL";

    /**
     * 加密
     *
     * @param content
     *            需要加密的内容
     * @return
     */
    public static byte[] encrypt2(String content) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(PWD.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content
     *            待解密内容
     * @return
     */
    public static byte[] decrypt2(byte[] content) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(PWD.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }else {
            byte[] result = new byte[hexStr.length() / 2];
            for (int i = 0; i < hexStr.length() / 2; i++) {
                int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte) (high * 16 + low);
            }
            return result;
        }

    }

    /**
     * 加密
     *
     * @param content
     *            需要加密的内容
     * @param password
     *            加密密码
     * @return
     */
    public static byte[] encrypt2(String content, String password) {
        try {
            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String encrypt(String content) throws Exception {

        byte[] encode = encrypt2(content);

        String code = parseByte2HexStr(encode);

        return code;
    }
    public String decrypt(String encryptStr) throws Exception {

        byte[] decode = parseHexStr2Byte(encryptStr);

        byte[] encode = decrypt2(decode);

        String code = new String(encode, "UTF-8");

        return code;
    }

    public static String encrypt3(String content) throws Exception {

        byte[] encode = encrypt2(content);

        String code = parseByte2HexStr(encode);

        return code;
    }
    public static String decrypt3(String encryptStr) throws Exception {
        byte[] decode = parseHexStr2Byte(encryptStr);

        byte[] encode = decrypt2(decode);

        String code = new String(encode, "UTF-8");

        return code;
    }


    public static void main(String[] args) throws Exception {
        Map map=new HashMap<String,String>();
//        map.put("expireTime","1964053510");
        map.put("systemSN","4C4C4544-0046-4E10-8031-CAC04F533532");
        LicenseDto dto = new LicenseDto();
        dto.setStartTime(1704791990000L);
        dto.setEndTime(1707470390000L);
        dto.setSystemSN("Y3HaRlRtspphK9W8v/4VUr1l4728jYrkAURR5cGpUTWVwy+8fD5741NkqLnZKEXJ");
//        dto.setType(0);
        dto.setLicenseVersion("1.0");
        dto.setType("试用版");
//        dto.setLicenseFireWall(0);
//        dto.setLicenseRouter(0);
//        dto.setLicenseHost(0);
//        dto.setLicenseUe(0);
        String content = JSONObject.toJSONString(dto);
        System.out.println("加密前：" + content);
//
        String encrypt = encrypt3(content);
        System.out.println("加密后：" + encrypt);

        String decrypt = decrypt3(encrypt);
        System.out.println("解密后：" + decrypt);

    }

}
