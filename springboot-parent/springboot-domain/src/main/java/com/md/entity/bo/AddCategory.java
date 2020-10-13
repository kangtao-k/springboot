package com.md.entity.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddCategory implements Serializable {
    private Integer cat_level;// 分类级别
    private String cat_name;// 分类名称
    private Integer cat_pid;// 分类父ID
}
