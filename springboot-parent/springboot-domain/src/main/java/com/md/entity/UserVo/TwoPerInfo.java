package com.md.entity.UserVo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TwoPerInfo {

    private Integer id;
    private String authName;
    private String path;
    private List<ThreePerInfo> children = new ArrayList<>();
}
