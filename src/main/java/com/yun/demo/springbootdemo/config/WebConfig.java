package com.yun.demo.springbootdemo.config;

import com.yun.demo.springbootdemo.interceptor.LimitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private LimitInterceptor limitInterceptor;

    /**
     * 自定义拦截器，添加拦截路径和排除拦截路径
     * @param registry registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 限制每个IP访问接口的次数，拦截所有接口
        registry.addInterceptor(this.limitInterceptor).addPathPatterns("/**");
    }

}
