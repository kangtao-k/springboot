package com.md.pojo.Statistics;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Table(name = "sp_report_1")
@Data
public class Report implements Serializable {

    @Id
    private Integer id;
    private Integer rp1_user_count;//用户数
    private String rp1_area;// 地区
    private Date rp1_date;
}
