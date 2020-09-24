package com.md.entity.UserVo;

import lombok.Data;

import java.io.Serializable;

//管理员登陆
@Data
public class ManagerLogin implements Serializable {

    private Integer id;
    private Integer rid;
    private String username;
    private String mobile;
    private String email;
    private String token;
}
