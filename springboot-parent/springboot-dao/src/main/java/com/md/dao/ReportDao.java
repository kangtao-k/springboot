package com.md.dao;

import com.md.pojo.Statistics.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface ReportDao {

    @Select("select * from sp_report_1")
    List<Report> findAllReport();

    @Select("SELECT distinct rp1_area FROM sp_report_1")
    List<String> findLegend();

    @Select("select distinct rp1_date from sp_report_1")
    List<String> findAllDate();

    @Select("select rp1_user_count from sp_report_1 where rp1_area=#{name}")
    List<Integer> findReportByName(@Param("name") String name);
}
