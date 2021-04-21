package com.djh.shanjupay.sms.generator;

import java.util.Random;

/**
 * 数字验证代码生成器
 *
 * @author MyMrDiao
 * @date 2021/04/20
 */
public class NumberVerificationCodeGenerator implements VerificationCodeGenerator {


    public NumberVerificationCodeGenerator(int len){
        this.len = len;
    }

    private int len;

    @Override
    public String generate() {
        return getNumRandom(len);
    }

    private  String getNumRandom(int length) {
        String val = "";
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }

}
