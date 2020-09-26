package com.md.entity.other;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddAttrs implements Serializable {

    private Integer attr_id;
    private String attr_value;
}
