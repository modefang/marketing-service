package com.yun.demo.springbootdemo.annotation;

import com.yun.demo.springbootdemo.constant.LimitIPConstant;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface LimitIP {

    int count() default LimitIPConstant.count;

    int second() default LimitIPConstant.second;

}
