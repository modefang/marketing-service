package com.yun.demo.springbootdemo.configuration;

import com.yun.demo.springbootdemo.interceptor.LimitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * 自定义拦截器，添加拦截路径和排除拦截路径
     * @param registry registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 限制每个IP访问接口的次数，拦截所有接口
        registry.addInterceptor(new LimitInterceptor()).addPathPatterns("/**");
    }

}
