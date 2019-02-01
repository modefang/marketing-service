package com.yun.demo.springbootdemo.pojo;

import lombok.Data;

@Data
public class TokenPojo {

    private String access_token;

    private String token_type;
    
    private Integer expires_in;
    
    private String refresh_token;
    
    private String scope;


}
