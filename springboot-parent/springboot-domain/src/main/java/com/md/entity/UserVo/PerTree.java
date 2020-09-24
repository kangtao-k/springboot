package com.md.entity.UserVo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PerTree<T> implements Serializable {

    private Integer id;
    private String authName;
    private String path;
    private Integer pid;
    private List<T> children = new ArrayList<>();

}
