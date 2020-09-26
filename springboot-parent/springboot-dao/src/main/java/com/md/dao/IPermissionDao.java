package com.md.dao;

import com.md.pojo.user.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IPermissionDao {

    @Select("select * from sp_permission")
    List<Permission> findPer() throws Exception;

    @Select("select * from sp_permission where ps_level=#{s}")
    List<Permission> findByLevel(@Param("s") String s);

    @Select("select * from sp_permission where ps_name=#{username}")
    Permission findByName(@Param("username") String username);

    @Select("select * from sp_permission where ps_id in(${psIds})")
    List<Permission> findByIds(@Param("psIds") String psIds);

    @Select("select * from sp_permission where ps_id=#{rightId}")
    Permission findById(@Param("rightId") Integer rightId) throws Exception;

    @Select("select ps_id from sp_permission where ps_pid=#{rightId}")
    List<Integer> IdByPid(@Param("rightId") Integer rightId) throws Exception;

    @Select("select ps_pid from sp_permission where ps_id=#{id}")
    Integer PidById(@Param("id") Integer id) throws Exception;

    @Select("select * from sp_permission where ps_pid=0")
    List<Permission> findByPid() throws Exception;

    @Select("select * from sp_permission where ps_pid <> 0 and ps_level='1'")
    List<Permission> findByNotPid() throws Exception;
}
