package com.md.pojo.user;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "sp_permission_api")
public class Per_api {

    @Id
    private Integer id;

    private Integer ps_id;          //权限id
    private String ps_api_service;
    private String ps_api_action;
    private String ps_api_path;     //路径
    private Integer ps_api_order;
}
