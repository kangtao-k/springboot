package com.md.dao;

import com.md.pojo.user.Manager;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface IManagerDao {

    @Select("select * from sp_manager where mg_name=#{username} and mg_pwd=#{password}")
    Manager loginManager(String username, String password) throws Exception;

    @Select("select * from sp_manager")
    List<Manager> findManager() throws Exception;

    @Select("insert into sp_manager(mg_name,mg_pwd,mg_time,mg_mobile,mg_email,mg_state) " +
            "values(#{username},#{password},#{time},#{mobile},#{email},#{type})")
    void addManager(String username, String password, String email, String mobile,int time,int type) throws Exception;

    @Select("select * from sp_manager where mg_name=#{username}")
    Manager findByName(String username) throws Exception;

    @Update("update sp_manager set mg_state=#{mg_state} where mg_id=#{uId}")
    void modifyType(Integer uId, int mg_state) throws Exception;

    @Select("select * from sp_manager where mg_id=#{uId}")
    Manager findById(Integer uId) throws Exception;

    @Update("update sp_manager set mg_email= #{email},mg_mobile=#{mobile} where mg_id = #{id}")
    void modifyById(String email, String mobile,Integer id)throws Exception;

    @Delete("delete from sp_manager where mg_id=#{id}")
    void delManager(Integer id)throws Exception;

    @Update("update sp_manager set role_id=#{rid} where mg_id=#{id}")
    void modifyRoleById(Integer id, Integer rid)throws Exception;

    @Select("select * from sp_manager limit #{a},#{pagesize}")
    List<Manager> findAllManager(int a, Integer pagesize);

    @Select("select * from sp_manager where mg_name like '%${query}%' limit #{a},#{p}")
    List<Manager> findByLikeName(String query,Integer a,Integer p);
}
