package com.md.entity.UserVo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ManagerModifyType implements Serializable {

    private Integer id;
    private Integer rid;
    private String username;
    private String mobile;
    private String email;
    private Integer mg_state;
}
