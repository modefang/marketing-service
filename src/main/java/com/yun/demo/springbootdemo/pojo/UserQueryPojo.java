package com.yun.demo.springbootdemo.pojo;

import lombok.Data;

@Data
public class UserQueryPojo {

    private String id;

    private String username;

    private String updateTimeStart;

    private String updateTimeEnd;

    private String createTimeStart;

    private String createTimeEnd;

}
