package com.yun.demo.springbootdemo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserEntity {

    private String id;

    private String name;

    private Date updateTime;

    private Date createTime;

}
