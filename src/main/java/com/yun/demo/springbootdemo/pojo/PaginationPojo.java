package com.yun.demo.springbootdemo.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PaginationPojo {

    private List<?> items;

    private Object total;

    public PaginationPojo(List<?> items, Object total) {
        this.items = items;
        this.total = total;
    }

}
