package com.md.pojo.goods;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ChildrenCats implements Serializable {

    private Integer cat_id;
    private String cat_name;
    private Integer cat_pid;
    private Integer cat_level;
    private Boolean cat_deleted;
    private List<ChildrenCats> children;
}
