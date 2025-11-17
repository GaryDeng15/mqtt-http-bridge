package com.gary.mqtthttpbridge.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 把java对象转json字符串
     *
     * @param object
     * @return
     */
    public static String writeValueAsString(Object object) {
        try {
            //把java对象转json字符串
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            //抛出我们自己定义的异常
            //throw new BusinessException(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 把json字符串转java对象
     *
     * @param json
     * @return
     */
    public static <T> T readValue(String json, Class<T> clazz) {
        try {
            //把json字符串转java对象
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            //抛出我们自己定义的异常
            //throw new BusinessException(e);
            throw new RuntimeException(e);
        }
    }
}
