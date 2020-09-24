package com.md.entity.UserVo;

import lombok.Data;

import java.util.List;

@Data
public class RolePerInfo {

    private Integer id;
    private String roleName;
    private String roleDesc;
    private List<OnePerInfo> children;
}
