package com.mall.model;

import java.io.Serializable;

public abstract class AbstractQuery implements Serializable {

    private Integer pageNum;
    private Integer pageSize;
    private String orderByBlock; // " a desc,b asc,c asc"


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderByBlock() {
        return orderByBlock;
    }

    public void setOrderByBlock(String orderByBlock) {
        this.orderByBlock = orderByBlock;
    }
}