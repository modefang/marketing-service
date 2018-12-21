package com.yun.demo.springbootdemo.constant;

public enum ResponseEnum {
    // 运行成功时返回的状态
    SUCCESS("00001", "success"),

    // 数据校验不通过时返回的状态
    WRONG_ID("10001", "错误的ID"),

    // 运行失败时返回的状态
    ERROR("20001", "error"),
    ERROR_DATABASE_INSERT("20002", "insert into database error")
    ;

    private String code;
    private String desc;

    ResponseEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
