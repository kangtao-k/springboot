package com.md.entity.other;

import lombok.Data;

import java.io.Serializable;

@Data
public class Series implements Serializable {

    private String name;// 地区名字
    private String type;
    private String stack;
    private AreaStyle areaStyle;
    private Object data;
}
