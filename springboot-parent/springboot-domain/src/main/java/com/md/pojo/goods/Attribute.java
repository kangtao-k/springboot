package com.md.pojo.goods;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class Attribute implements Serializable {

    @Id
    private Integer attr_id;// 主键
    private String attr_name;// 属性名称
    private Integer cat_id;// 外键，类型id
    private String attr_sel;// only:输入框(唯一)  many:后台下拉列表/前台单选框
    private String attr_write;// manual:手工录入  list:从列表选择
    private String attr_vals;// 可选值列表信息,例如颜色：白色,红色,绿色,多个可选值通过逗号分隔
    private Integer delete_time;// 删除时间标志
}
