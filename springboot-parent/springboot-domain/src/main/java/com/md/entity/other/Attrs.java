package com.md.entity.other;

import lombok.Data;

import java.io.Serializable;

@Data
public class Attrs implements Serializable {

    private Integer goods_id;
    private Integer attr_id;
    private String attr_value;
    private Integer add_price;
    private String attr_name;
    private String attr_sel;
    private String attr_write;
    private String attr_vals;
}
