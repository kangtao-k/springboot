package com.md.pojo.goods;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "sp_goods_pics")
@Data
public class GoodsPics implements Serializable {

    @Id
    private Integer pics_id;// 主键id
    private Integer goods_id;// 商品id
    private String pics_big;// 相册大图800*800
    private String pics_mid;// 相册中图350*350
    private String pics_sma;// 相册小图50*50

    private String pics_big_url;// 相册大图800*800
    private String pics_mid_url;// 相册中图350*350
    private String pics_sma_url;// 相册小图50*50
}

