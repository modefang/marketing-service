package com.yun.demo.springbootdemo.exception;

import com.yun.demo.springbootdemo.constant.ResponseEnum;

public class GlobalDefultException extends RuntimeException {

    private String code;

    public GlobalDefultException() {}

    public GlobalDefultException(ResponseEnum e) {
        super(e.getDesc());
        this.code = e.getCode();
    }

    public String getCode() {
        return code;
    }

}
