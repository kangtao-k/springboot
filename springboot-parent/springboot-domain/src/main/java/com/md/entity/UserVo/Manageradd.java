package com.md.entity.UserVo;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

//### 1.3.2. 添加用户
@Data
public class Manageradd implements Serializable {

    @Id
    private Integer id;
    private String username;        //用户名
    private String email;           //邮箱
    private String mobile;          //手机号
    private Integer create_time;     //创建时间
    private Integer role_id;        //角色id

}
