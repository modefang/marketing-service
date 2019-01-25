//package com.yun.demo.springbootdemo.config.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        //如果要自定义加密方式，此处就应该返回自定义的passwordencoder，再实现encoder和matches方法
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .authorizeRequests()
//                .antMatchers("/auth/**").authenticated()
//                .anyRequest().permitAll()
//                .and()
//            .formLogin()
////                .loginPage("/login") // 自定义登录页面
//                .permitAll()
//                .and()
//            .logout()
//                .logoutUrl("/auth/logout")
//                .logoutSuccessUrl("/login?logout")
//                .permitAll();
//    }
//
//}
