package com.md.pojo.goods;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "sp_goods_cats")
@Data
public class GoodsCats implements Serializable {

    @Id
    private Integer cat_id;// 父类id
    private Integer parent_id;// 父级id
    private String cat_name;// 分类名称
    private Integer is_show;// 是否显示
    private Integer cat_sort;// 分类排序
    private Integer data_flag;// 数据标记
    private Integer create_time;// 创建时间
}
