package com.yun.demo.springbootdemo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    public static String objectToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object jsonToObject(String json, Class _class) {
        try {
            return new ObjectMapper().readValue(json, _class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
