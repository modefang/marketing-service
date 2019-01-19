package com.yun.demo.springbootdemo.interceptor;

import com.yun.demo.springbootdemo.annotation.LimitRequest;
import com.yun.demo.springbootdemo.constant.LimitRequestConstant;
import com.yun.demo.springbootdemo.constant.ResponseEnum;
import com.yun.demo.springbootdemo.pojo.ResponsePojo;
import com.yun.demo.springbootdemo.util.CommonUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Component
public class LimitInterceptor implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 过滤资源请求
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }
        try {
            // 校验请求次数是否超出限制
            return limitRequest(request, response, handler);
        } catch (Exception e) {
            CommonUtil.sendJsonMessage(response, new ResponsePojo(ResponseEnum.ERROR, -1));
            return false;
        }
    }

    /**
     * 校验请求次数是否超出限制
     */
    private boolean limitRequest(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 初始化参数
        long times = LimitRequestConstant.TIMES;
        long seconds = LimitRequestConstant.SECONDS;

        // 判断请求的controller方法是否有注解@LimitRequest
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        LimitRequest limit = method.getAnnotation(LimitRequest.class);
        // 有注解，则更新参数
        if (limit != null) {
            times = limit.times();
            seconds = limit.seconds();
        }

        String ip = request.getRemoteAddr();
        String redisKey = "limit-ip:" + ip;
        // 设置在redis中的缓存，累加1
        Long current = stringRedisTemplate.opsForValue().increment(redisKey);
        if (current == null) {
            throw new Exception();
        }
        if (current > times) {
            CommonUtil.sendJsonMessage(response, new ResponsePojo(ResponseEnum.ERROR_REQUEST_EXCEEDS_LIMIT, -1));
            return false;
        }
        // 如果该key不存在，则从0开始加1，最后返回1
        if (current == 1) {
            stringRedisTemplate.expire(redisKey, seconds, TimeUnit.SECONDS);
        }
        return true;
    }

}
