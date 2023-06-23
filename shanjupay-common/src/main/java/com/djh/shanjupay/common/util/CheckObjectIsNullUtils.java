package com.djh.shanjupay.common.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * 判断对象是否为空，进一步判断对象中的属性是否都为空
 *
 * @author MyMrDiao
 * @date 2023/01/10
 */
@Slf4j
public class CheckObjectIsNullUtils {

    /**
     * 判断对象是否为空，且其中所有属性是否为空
     * ps：boolean类型会有默认值false 判断结果不会为null 会影响判断结果 序列化的默认值也会影响判断结果
     *
     * @param obj    判断对象
     * @param ignore 忽略对象属性
     * @return boolean
     */
    public static boolean objCheckIsNull(Object obj, String ignore) {
        // 得到类对象
        Class clazz = obj.getClass();
        // 得到所有属性
        Field[] fields = clazz.getDeclaredFields();
        // 定义返回结果，默认为true
        boolean flag = true;
        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                // 等到属性值
                fieldValue = field.get(obj);
                // 得到属性类型
                Type fieldType = field.getGenericType();
                // 得到属性名
                String name = field.getName();
                if (name.equals(ignore)) {
                    continue;
                }
                log.info("属性类型：{}，属性名：{}，属性值：{}", fieldType, name, fieldValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (fieldValue != null) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
