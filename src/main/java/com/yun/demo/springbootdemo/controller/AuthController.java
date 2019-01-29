package com.yun.demo.springbootdemo.controller;

import com.yun.demo.springbootdemo.constant.ResponseEnum;
import com.yun.demo.springbootdemo.pojo.ResponsePojo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    @RequestMapping("/auth/code")
    public String code(String code){
        return "code: " + code;
    }

    @RequestMapping("/auth/add")
    public ResponsePojo add() {
        return new ResponsePojo(ResponseEnum.SUCCESS, "add page");
    }

    @RequestMapping("/auth/search")
    public ResponsePojo search() {
        return new ResponsePojo(ResponseEnum.SUCCESS, "search page");
    }

    @RequestMapping("/auth/update")
    public ResponsePojo update() {
        return new ResponsePojo(ResponseEnum.SUCCESS, "update page");
    }

    @RequestMapping("/auth/delete")
    public ResponsePojo delete() {
        return new ResponsePojo(ResponseEnum.SUCCESS, "delete page");
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response){
        return "login";
    }

    @RequestMapping("/auth/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping("/auth/hello")
    public String hello(){
        return "Hello, spring security.";
    }

}
