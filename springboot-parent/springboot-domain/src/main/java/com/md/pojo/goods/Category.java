package com.md.pojo.goods;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 商品分类
 */
@Table(name = "sp_category")
@Data
public class Category implements Serializable {

    @Id
    private Integer cat_id;// 分类唯一id
    private String cat_name;// 分类名称
    private Integer cat_pid;// 分类父ID
    private Integer cat_level;// 分类层级 0:顶级 1:二级 2:三级
    private Integer cat_deleted;// 是否删除 1为删除
    private String cat_icon;
    private String src;
}
