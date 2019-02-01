package com.yun.demo.springbootdemo.controller;

import com.yun.demo.springbootdemo.pojo.TokenPojo;
import com.yun.demo.springbootdemo.util.CommonUtils;
import com.yun.demo.springbootdemo.util.JsonUtils;
import com.yun.demo.springbootdemo.util.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    final String CUSTOM_REDIRECT_URL = "http://localhost:8080/auth/user";

    final String REDIRECT_URI = "http://localhost:8080/code";

//    final String TOKEN_URL = "http://localhost:9080/oauth/token?grant_type=authorization_code&client_id=mo&client_secret=modefang&code={CODE}&redirect_uri={REDIRECT_URI}";

    final String TOKEN_URL = "http://localhost:9080/oauth/token";

    final String AUTH_URL = "http://localhost:9080/oauth/authorize?response_type=code&client_id=mo&state={STATE}&redirect_uri={REDIRECT_URI}";

    String ACCESS_TOKEN = "";

    @RequestMapping("/hello")
    public String hello(){
        return "Hello, client server.";
    }

    @RequestMapping("/user")
    public Authentication getUser(Authentication authentication) {
        return authentication;
    }

    @RequestMapping("/code")
    public String code(HttpServletResponse response, String code, String state) throws Exception {
        String redirectUri = URLEncoder.encode(REDIRECT_URI, "UTF-8");
        if (StringUtils.isEmpty(ACCESS_TOKEN) && StringUtils.isEmpty(code)) {
            String costomRedirectUrl = URLEncoder.encode(CUSTOM_REDIRECT_URL, "UTF-8");
            String authUrl = AUTH_URL.replace("{STATE}", costomRedirectUrl).replace("{REDIRECT_URI}", redirectUri);
            response.sendRedirect(authUrl);
            return "";
        }

//        String tokenUrl = TOKEN_URL.replace("{CODE}", code).replace("{REDIRECT_URI}", redirectUri);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("grant_type", Collections.singletonList("authorization_code"));
        map.put("client_id", Collections.singletonList("mo"));
        map.put("client_secret", Collections.singletonList("modefang"));
        map.put("code", Collections.singletonList(code));
        map.put("redirect_uri", Collections.singletonList(redirectUri));
        String body = JsonUtils.objectToJson(map);
        String result = CommonUtils.callApi(TOKEN_URL, HttpMethod.POST, body);
//        String result = CommonUtils.callApi(tokenUrl, HttpMethod.POST);
        TokenPojo token = (TokenPojo) JsonUtils.jsonToObject(result, TokenPojo.class);
        if (token == null) {
            return "error";
//            return;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token.getToken_type() + " " + token.getAccess_token());
        String user = CommonUtils.callApi(state, HttpMethod.POST, headers);
        System.out.println(user);
        return user;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

}
