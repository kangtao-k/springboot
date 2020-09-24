package com.md.pojo.user;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "sp_Permission")
public class Permission {

    @Id
    private Integer ps_id;      //权限id

    private String ps_name;     //权限名称
    private Integer ps_pid;     //权限父id
    private String ps_c;        //控制器
    private String ps_a;        //操作方法
    private String ps_level;    //权限等级

}
