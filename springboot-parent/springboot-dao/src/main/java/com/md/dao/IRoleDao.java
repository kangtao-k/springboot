package com.md.dao;

import com.md.pojo.user.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IRoleDao {

    @Select("select role_name from sp_role where role_id=#{mg_id}")
    String NameById(@Param("mg_id") Integer mg_id) throws Exception;

    @Select("select * from sp_role")
    List<Role> findRole() throws Exception;

    @Insert("insert into sp_role(role_name,role_desc) values(#{roleName},#{roleDesc})")
    void addRole(@Param("roleName") String roleName, @Param("roleDesc") String roleDesc) throws Exception;

    @Select("select * from sp_role where role_name=#{roleName}")
    Role findByName(@Param("roleName") String roleName) throws Exception;

    @Select("select * from sp_role where role_id=#{id}")
    Role findById(@Param("id") Integer id) throws Exception;

    @Update("update sp_role set role_name=#{roleName},role_desc=#{roleDesc} where role_id=#{id}")
    void modifyRole(@Param("id") Integer id, @Param("roleName") String roleName, @Param("roleDesc") String roleDesc);

    @Delete("delete from sp_role where role_id=#{id}")
    void delRoleById(@Param("id") Integer id) throws Exception;

    @Update("update sp_role set ps_ids=#{rids} where role_id=#{id}")
    void modifyRights(@Param("id") Integer id, @Param("rids") String rids) throws Exception;

    @Select("select ps_ids from sp_role where role_id=#{roleId}")
    String getIdsById(@Param("roleId") Integer roleId) throws Exception;
}
