package com.yun.demo.springbootdemo.util;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class CommonUtils {

    /**
     * 返回json数据
     */
    public static void sendJsonMessage(HttpServletResponse response, Object object) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(JsonUtils.objectToJson(object));
        writer.close();
        response.flushBuffer();
    }

    /**
     * 调用API接口
     * @param url api链接
     * @param method 请求方式 { GET, POST, ... }
     * @return API返回的字符串
     */
    public static String callApi(String url, HttpMethod method) {
        return callApi(url, method, null, null);
    }

    /**
     * 调用API接口
     * @param url api链接
     * @param method 请求方式 { GET, POST, ... }
     * @param body json字符串
     * @return API返回的字符串
     */
    public static String callApi(String url, HttpMethod method, Object body) {
        return callApi(url, method, null, body);
    }

    /**
     * 调用API接口
     * @param url api链接
     * @param method 请求方式 { GET, POST, ... }
     * @param headers 请求头信息
     * @return API返回的字符串
     */
    public static String callApi(String url, HttpMethod method, HttpHeaders headers) {
        return callApi(url, method, headers, null);
    }

    /**
     * 调用API接口
     * @param url api链接
     * @param method 请求方式 { GET, POST, ... }
     * @param headers 请求头信息
     * @param body json字符串
     * @return API返回的字符串
     */
    public static String callApi(String url, HttpMethod method, HttpHeaders headers, Object body) {
        if (headers == null) {
            headers = new HttpHeaders();
        }
        // 设定以json的方式传输body数据，并解决中文乱码
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        // http请求携带的信息
        HttpEntity entity = new HttpEntity<> (body, headers);

        RestTemplate restTemplate = new RestTemplate();
        // 向url发送method类型的请求，请求携带信息entity，返回String.class类型的数据
        ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);

        return response.getBody();
    }

}
