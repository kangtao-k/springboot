package com.md.entity.UserVo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RolePerInfo {

    private Integer id;
    private String roleName;
    private String roleDesc;
    private List<PerList> children = new ArrayList<>();
}
