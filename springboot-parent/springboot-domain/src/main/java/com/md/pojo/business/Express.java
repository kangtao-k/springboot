package com.md.pojo.business;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "sp_express")
@Data
public class Express implements Serializable {

    @Id
    private Integer express_id;// 主键id
    private Integer order_id;// 订单id
    private String express_com;// 订单快递公司名称
    private String express_nu;// 快递单编号
    private Integer create_time;// 记录生产时间
    private Integer update_time;// 记录修改时间
}
