package com.md.entity.UserVo;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class Managerfind implements Serializable {

    @Id
    private Integer id;

    private String username;        //用户名
    private String email;           //邮箱
    private String mobile;          //手机号
    private Integer type;           //状态   1
    private Integer create_time;     //创建时间
    private boolean mg_state;       //状态 1为true 0为false
    private String role_name;       //角色名
}
