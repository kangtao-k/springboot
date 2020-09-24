package com.md.dao;

import com.md.pojo.user.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IRoleDao {

    @Select("select role_name from sp_role where role_id=#{mg_id}")
    String NameById(Integer mg_id) throws Exception;

    @Select("select * from sp_role")
    List<Role> findRole() throws Exception;

    @Insert("insert into sp_role(role_name,role_desc) values(#{roleName},#{roleDesc})")
    void addRole(String roleName, String roleDesc) throws Exception;

    @Select("select * from sp_role where role_name=#{roleName}")
    Role findByName(String roleName) throws Exception;

    @Select("select * from sp_role where role_id=#{id}")
    Role findById(Integer id) throws Exception;

    @Update("update sp_role set role_name=#{roleName},role_desc=#{roleDesc} where role_id=#{id}")
    void modifyRole(Integer id, String roleName, String roleDesc);

    @Delete("delete from sp_role where role_id=#{id}")
    void delRoleById(Integer id) throws Exception;

    @Update("update sp_role set ps_ids=#{rids} where role_id=#{id}")
    void modifyRights(Integer id, String rids) throws Exception;

    @Select("select ps_ids from sp_role where role_id=#{roleId}")
    String getIdsById(Integer roleId) throws Exception;
}
