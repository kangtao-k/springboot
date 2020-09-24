package com.md.pojo.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

//管理员账号
@Table(name = "sp_manager")
public class Manager implements Serializable {

    @Id
    private Integer mg_id;             //主键id

    private String mg_name;         //名称
    private String mg_pwd;          //密码
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Integer mg_time;        //注册时间
    private Integer role_id;        //管理员角色id
    private String mg_mobile;       //手机号
    private String mg_email;        //邮箱
    private Integer mg_state;       //1.表示启用，0.表示禁用
    //private Role role;              //角色

    @Override
    public String toString() {
        return "Manager{" +
                "mg_id=" + mg_id +
                ", mg_name='" + mg_name + '\'' +
                ", mg_pwd='" + mg_pwd + '\'' +
                ", mg_time='" + mg_time + '\'' +
                ", role_id=" + role_id +
                ", mg_mobile='" + mg_mobile + '\'' +
                ", mg_email='" + mg_email + '\'' +
                ", mg_state=" + mg_state +
                '}';
    }

    public Integer getMg_id() {
        return mg_id;
    }

    public void setMg_id(Integer mg_id) {
        this.mg_id = mg_id;
    }

    public String getMg_name() {
        return mg_name;
    }

    public void setMg_name(String mg_name) {
        this.mg_name = mg_name;
    }

    public String getMg_pwd() {
        return mg_pwd;
    }

    public void setMg_pwd(String mg_pwd) {
        this.mg_pwd = mg_pwd;
    }

    public Integer getMg_time() {
        return mg_time;
    }

    public void setMg_time(Integer mg_time) {
        this.mg_time = mg_time;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getMg_mobile() {
        return mg_mobile;
    }

    public void setMg_mobile(String mg_mobile) {
        this.mg_mobile = mg_mobile;
    }

    public String getMg_email() {
        return mg_email;
    }

    public void setMg_email(String mg_email) {
        this.mg_email = mg_email;
    }

    public Integer getMg_state() {
        return mg_state;
    }

    public void setMg_state(Integer mg_state) {
        this.mg_state = mg_state;
    }
}
