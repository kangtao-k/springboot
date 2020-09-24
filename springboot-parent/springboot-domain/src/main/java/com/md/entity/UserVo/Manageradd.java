package com.md.entity.UserVo;

import javax.persistence.Id;
import java.io.Serializable;

//### 1.3.2. 添加用户
public class Manageradd implements Serializable {

    @Id
    private Integer id;
    private String username;        //用户名
    private String email;           //邮箱
    private String mobile;          //手机号
    private Integer type;           //状态   1
    private String openid;
    private String create_time;     //创建时间
    private String modify_time;
    private boolean is_delete = false;      //是否已删除用户   false
    private boolean is_active = false;      //是否在登陆     true


    @Override
    public String toString() {
        return "Manageradd{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", type=" + type +
                ", openid='" + openid + '\'' +
                ", create_time='" + create_time + '\'' +
                ", modify_time='" + modify_time + '\'' +
                ", is_delete=" + is_delete +
                ", is_active=" + is_active +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getModify_time() {
        return modify_time;
    }

    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }

    public boolean isIs_delete() {
        return is_delete;
    }

    public void setIs_delete(boolean is_delete) {

        this.is_delete = is_delete;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
