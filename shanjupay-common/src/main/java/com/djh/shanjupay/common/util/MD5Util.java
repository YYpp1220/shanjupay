package com.djh.shanjupay.common.util;

import com.djh.shanjupay.common.constant.ShanjuPayConstant;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


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

    /**
     * 生成key
     *
     * @param token 密码
     * @return {@link Key}
     * @throws Exception 异常
     */
    private static Key generateKey(String token) throws Exception {
        DESKeySpec dks = new DESKeySpec(token.getBytes(ShanjuPayConstant.CHARSET));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ShanjuPayConstant.ALGORITHM);
        return keyFactory.generateSecret(dks);
    }

    /**
     * DES加密字符串
     *
     * @param token 加密密码，长度不能够小于8位
     * @param data 待加密字符串
     * @return 加密后内容
     */
    public static String encrypt(String token, String data) {
        if (data == null)
            return null;
        try {
            Key secretKey = generateKey(token);
            Cipher cipher = Cipher.getInstance(ShanjuPayConstant.CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(ShanjuPayConstant.IV_PARAMETER.getBytes(ShanjuPayConstant.CHARSET));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

            // 加密
            byte[] bytes = cipher.doFinal(data.getBytes(ShanjuPayConstant.CHARSET));

            // base64编码  JDK1.8及以上可直接使用Base64，JDK1.7及以下可以使用BASE64Encoder
            byte[] encode = Base64.getEncoder().encode(bytes);

            return new String(encode);

        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    /**
     * DES解密字符串
     *
     * @param token 解密密码，长度不能够小于8位
     * @param data 待解密字符串
     * @return 解密后内容
     */
    public static String decrypt(String token, String data) {
        if (data == null)
            return null;
        try {
            Key secretKey = generateKey(token);
            Cipher cipher = Cipher.getInstance(ShanjuPayConstant.CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(ShanjuPayConstant.IV_PARAMETER.getBytes(ShanjuPayConstant.CHARSET));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

            // base64解码
            byte[] decode = Base64.getDecoder().decode(data.getBytes(ShanjuPayConstant.CHARSET));

            // 解密
            byte[] decrypt = cipher.doFinal(decode);

            return new String(decrypt, ShanjuPayConstant.CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    public static void main(String[] args) {
        /*String nihao = getMd5("123456string");
        System.out.println(nihao);*/
        String content = "login_token_d95b6887-7224-4362-9cfb-02894b52fe1f";

        String encptStr = encrypt(ShanjuPayConstant.KEY,content);
        System.out.println(encptStr);

        String decptStr = decrypt(ShanjuPayConstant.KEY,encptStr);
        System.out.println(decptStr);
    }
}
