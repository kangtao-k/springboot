package com.md.common;

import java.io.Serializable;
import java.util.List;

public class PageObj<T> implements Serializable {

    private List<T> users;
    private Integer total;          //总记录数
    private Integer pageNum;            //当前页码

    public List<T> getUsers() {
        return users;
    }

    public void setUsers(List<T> users) {
        this.users = users;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
