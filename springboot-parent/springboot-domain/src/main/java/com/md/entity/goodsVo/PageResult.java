package com.md.entity.goodsVo;

import com.md.pojo.goods.Goods;
import com.md.pojo.goods.Goods_2;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品分页结果
 */
@Data
public class PageResult implements Serializable {

    private Long total;// 返回记录数
    private String pagenum;// 当前页码
    private List<Goods> goods;// 商品结果

    public static PageResult succ(Long total,String pagenum,List<Goods> goods){
        PageResult result = new PageResult();
        result.setTotal(total);
        result.setPagenum(pagenum);
        result.setGoods(goods);
        return result;
    }
}
