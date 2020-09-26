package com.md.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IPer_apiDao {

    @Select("select ps_api_path from sp_permission_api where ps_id=#{ps_id}")
    String pathById(@Param("ps_id") Integer ps_id) throws Exception;
}
