package com.yun.demo.springbootdemo.pojo;

import lombok.Data;

@Data
public class PagePojo {

    private final int MAX_SIZE = 500;

    private Integer index;

    private int size;

    private int skip;

    private String sortStr;

    public PagePojo(Integer index) {
        this.index = index != null && index > 0 ? index : 1;
        this.size = 10;
        this.skip = this.countSkip();
    }

    public PagePojo(Integer index, int size) {
        this.index = index != null && index > 0 ? index : 1;
        this.size = size;
        this.skip = this.countSkip();
    }

    public PagePojo(Integer index, String sort) {
        this.index = index != null && index > 0 ? index : 1;
        this.size = 10;
        this.skip = this.countSkip();
        this.sortStr = sort;
    }

    public PagePojo(Integer index, int size, String sort) {
        this.index = index != null && index > 0 ? index : 1;
        this.size = size;
        this.skip = this.countSkip();
        this.sortStr = sort;
    }
    
    private int countSkip() {
        int skip = (this.index - 1) * this.size;
        int maxSkip = this.MAX_SIZE - this.size;
        return skip > maxSkip ? maxSkip : skip;
    }

}
