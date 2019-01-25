package com.yun.demo.springbootdemo.util;

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

}
