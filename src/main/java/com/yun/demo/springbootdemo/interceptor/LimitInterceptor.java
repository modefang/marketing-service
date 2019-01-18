package com.yun.demo.springbootdemo.interceptor;

import com.yun.demo.springbootdemo.annotation.LimitIP;
import com.yun.demo.springbootdemo.constant.LimitIPConstant;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class LimitInterceptor implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 初始化参数
        int count = LimitIPConstant.count;
        int second = LimitIPConstant.second;

        // 判断请求的controller方法是否有注解@LimitIP
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        LimitIP limit = method.getAnnotation(LimitIP.class);
        // 有注解，则更新参数
        if(limit != null) {
            count = limit.count();
            second = limit.second();
        }

        String ip = request.getRemoteAddr();
        String redisKey = "limit-ip:" + ip;
        // 设置在redis中的缓存，累加1
//        long count = stringRedisTemplate.opsForValue().increment(redisKey);
//
//        // 如果该key不存在，则从0开始计算，并且当count为1的时候，设置过期时间
//        if (count == 1) {
//            jedis.expire(redisKey, limit.timeSecond());
////                redisTemplate.expire(redisKey, limit.time(), TimeUnit.MILLISECONDS);
//        }
//
//        // 如果redis中的count大于限制的次数，则报错
//        if (count > limit.limitCounts()) {
//            // logger.info("用户IP[" + ip + "]访问地址[" + url + "]超过了限定的次数[" + limit.count() + "]");
//            if (ShiroFilterUtils.isAjax(request)) {
//                HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
//                httpServletResponse.sendError(ShiroFilterUtils.HTTP_STATUS_LIMIT_IP_REQUEST);
//            } else {
//                throw new LimitIPRequestException();
//            }
//        }
        return true;
    }

}
