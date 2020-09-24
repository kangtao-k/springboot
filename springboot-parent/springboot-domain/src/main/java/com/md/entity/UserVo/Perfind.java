package com.md.entity.UserVo;

import lombok.Data;

@Data
public class Perfind {

    private Integer id;
    private String authName;    //权限描述
    private String level;       //权限等级
    private Integer pid;        //权限父id
    private String path;        //权限路径
}
