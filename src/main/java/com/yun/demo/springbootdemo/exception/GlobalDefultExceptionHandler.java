package com.yun.demo.springbootdemo.exception;

import com.yun.demo.springbootdemo.constant.ResponseEnum;
import com.yun.demo.springbootdemo.pojo.ResponsePojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestControllerAdvice
public class GlobalDefultExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalDefultExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    public ResponsePojo defultExcepitonHandler(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        ResponsePojo responsePojo = new ResponsePojo(ResponseEnum.ERROR);
        if (e instanceof GlobalDefultException) {
            GlobalDefultException globalDefultException = (GlobalDefultException) e;
            responsePojo = new ResponsePojo(globalDefultException.getCode(), globalDefultException.getMessage());
        }
        logger(request, responsePojo.getDesc());
        return responsePojo;
    }

    /**
     * 日志记录
     * @param request 请求
     * @param message 异常信息
     */
    private void logger(HttpServletRequest request, String message) {
        // 获取请求里的所有参数，并拼接成字符串
        Enumeration enumeration = request.getParameterNames();
        StringBuilder parameters = new StringBuilder("[ ");
        int flag = 0;
        while (enumeration.hasMoreElements()) {
            if (flag == 1) {
                parameters.append(", ");
            } else {
                flag = 1;
            }
            String name = enumeration.nextElement().toString();
            parameters.append(name).append(": ").append(request.getParameter(name));
        }
        parameters.append(" ]");

        logger.error("--------------------------异常开始--------------------------");
        logger.error("异常信息：" + message);
        logger.error("请求地址：" + request.getRequestURL());
        logger.error("请求参数：" + parameters);
        logger.error("--------------------------异常结束--------------------------");
    }

}
