package com.md.dao;

import com.md.pojo.user.Manager;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IManagerDao {

    @Select("select * from sp_manager where mg_name=#{username} and mg_pwd=#{password}")
    Manager loginManager(@Param("username") String username, @Param("password") String password) throws Exception;

    @Select("select * from sp_manager")
    List<Manager> findManager() throws Exception;

    @Select("insert into sp_manager(mg_name,mg_pwd,mg_time,mg_mobile,mg_email,mg_state) " +
            "values(#{username},#{password},#{time},#{mobile},#{email},#{type})")
    void addManager(@Param("username") String username, @Param("password") String password, @Param("email") String email,
                    @Param("mobile") String mobile, @Param("time") int time, @Param("type") int type) throws Exception;

    @Select("select * from sp_manager where mg_name=#{username}")
    Manager findByName(@Param("username") String username) throws Exception;

    @Update("update sp_manager set mg_state=#{mg_state} where mg_id=#{uId}")
    void modifyType(@Param("uId") Integer uId, @Param("mg_state") int mg_state) throws Exception;

    @Select("select * from sp_manager where mg_id=#{uId}")
    Manager findById(@Param("uId") Integer uId) throws Exception;

    @Update("update sp_manager set mg_email= #{email},mg_mobile=#{mobile} where mg_id = #{id}")
    void modifyById(@Param("email") String email, @Param("mobile") String mobile, @Param("id") Integer id) throws Exception;

    @Delete("delete from sp_manager where mg_id=#{id}")
    void delManager(@Param("id") Integer id) throws Exception;

    @Update("update sp_manager set role_id=#{rid} where mg_id=#{id}")
    void modifyRoleById(@Param("id") Integer id, @Param("rid") Integer rid) throws Exception;

    @Select("select * from sp_manager limit #{a},#{pagesize}")
    List<Manager> findAllManager(@Param("a") int a, @Param("pagesize") Integer pagesize);

    @Select("select * from sp_manager where mg_name like '%${query}%' limit #{a},#{p}")
    List<Manager> findByLikeName(@Param("query") String query, @Param("a") Integer a, @Param("p") Integer p);

    @Select("select count(*) from sp_manager")
    Integer mannum() throws Exception;

    @Select("select count(*) from sp_manager where mg_name like '%${query}%'")
    Integer findTotalByLikeName(@Param("query") String query);

    @Select("select role_id from sp_manager where mg_id=#{id}")
    Integer roleIdById(@Param("id") Integer id) throws Exception;
}
