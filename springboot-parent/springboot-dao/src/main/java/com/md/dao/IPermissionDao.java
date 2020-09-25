package com.md.dao;

import com.md.pojo.user.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IPermissionDao {

    @Select("select * from sp_permission")
    List<Permission> findPer()throws Exception;

    @Select("select * from sp_permission where ps_level=#{s}")
    List<Permission> findByLevel(String s);

    @Select("select * from sp_permission where ps_name=#{username}")
    Permission findByName(String username);

    @Select("select * from sp_permission where ps_id in(${psIds})")
    List<Permission> findByIds(@Param("psIds") String psIds);

    @Select("select * from sp_permission where ps_id=#{rightId}")
    Permission findById(Integer rightId) throws Exception;

    @Select("select ps_id from sp_permission where ps_pid=#{rightId}")
    List<Integer> IdByPid(Integer rightId) throws Exception;

    @Select("select ps_pid from sp_permission where ps_id=#{id}")
    Integer PidById(Integer id) throws Exception;

    @Select("select * from sp_permission where ps_pid=0")
    List<Permission> findByPid() throws Exception;

    @Select("select * from sp_permission where ps_pid <> 0 and ps_level='1'")
    List<Permission> findByNotPid() throws Exception;

    @Select("select * from sp_permission where ps_level=#{s} and ps_pid in (#{psIds}) ")
    List<Permission> RolePerByLevel(String psIds,String s) throws Exception;

}
