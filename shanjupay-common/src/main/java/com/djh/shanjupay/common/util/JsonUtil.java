package com.djh.shanjupay.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * json工具类
 *
 * @author MrMyHui
 * @date 2021/04/01
 */
public class JsonUtil {

    /**
     * json对象
     *
     * @param object 对象
     * @return {@link String}
     */
    public static String objectToJson(Object object){
        return JSON.toJSONString(object,SerializerFeature.WriteDateUseDateFormat);
    }


    /**
     * 列表json
     *
     * @param list 列表
     * @return {@link String}
     */
    public static String listToJson(List list){
        return JSON.toJSONString(list, SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 字符串Json格式转换为对象Map
     * @param strJson {"username":"sxb"}
     * @return 根据json转换为Map对象
     */
    public static Map<String, Object> jsonToMap(String strJson){
        Map jsoMap = new HashMap<String, Object>(16);
        try {
            jsoMap = JSONObject.parseObject(strJson,Map.class);
        } catch (JSONException e) {
            System.out.println("json转换Map出错："+e.getMessage());
        }
        
        return jsoMap;
    }


    /**
     * 字符串Json 转换为对象List
     * @param strJson [{"username":"sxb"}]
     * @return 根据json转换List
     */
    public static List<Map<String, Object>> jsonToList(String strJson){
        List list = new ArrayList<Map<String, Object>>();
        try {
            list = JSONObject.parseObject(strJson, List.class);
        } catch (JSONException e) {
            System.out.println("json转换List出错："+e.getMessage());
        }
        return list;
    }
    

}
