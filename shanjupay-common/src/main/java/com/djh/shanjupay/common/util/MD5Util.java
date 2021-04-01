package com.djh.shanjupay.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * md5算法工具类
 *
 * @author MrMyHui
 * @date 2021/04/01
 */
public class MD5Util {
    /**
     * 获取字符串的MD5摘要计算结果
     *
     * @param plainText 纯文本
     * @return {@link String}
     */
    public static String getMd5(String plainText) {  
        try {  
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());  
            byte b[] = md.digest();  
  
            int i;  
  
            StringBuffer buf = new StringBuffer("");
            for (byte value : b) {
                i = value;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }  
            //32位加密  
            return buf.toString();  
            // 16位的加密  
            //return buf.toString().substring(8, 24);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
            return null;  
        }  
  
    }

    //public static void main(String[] args) {
    //    String nihao = getMd5("123456string");
    //    System.out.println(nihao);
    //}
}
