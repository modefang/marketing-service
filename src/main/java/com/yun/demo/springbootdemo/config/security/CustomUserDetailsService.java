//package com.yun.demo.springbootdemo.config.security;
//
//import com.yun.demo.springbootdemo.entity.UserEntity;
//import com.yun.demo.springbootdemo.service.UserService;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//@Component
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Resource
//    private UserService userService;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserEntity user = userService.loadUserByUsername(username);
//        return new User(user.getUsername(), user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("user"));
//    }
//
//}
