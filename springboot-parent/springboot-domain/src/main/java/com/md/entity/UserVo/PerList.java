package com.md.entity.UserVo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PerList implements Serializable {
    private Integer id;
    private String authName;
    private String path;
    private List<PerList> children = new ArrayList<>();
}
