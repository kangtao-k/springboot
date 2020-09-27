package com.md.service.goods;

import com.md.entity.bo.AddGoods;
import com.md.entity.other.Attrs;
import com.md.pojo.goods.Goods;
import com.md.pojo.goods.GoodsAttr;
import com.md.pojo.goods.GoodsPics;
import com.md.pojo.goods.Goods_2;

import java.util.List;

/**
 * 商品业务层
 */
public interface SpGoodsService {

    List<Goods> findAll(Integer pageNum,Integer pageSize) throws Exception;

    Goods addGoods(AddGoods goods) throws Exception;

    List<GoodsPics> findGoodsPics(Integer goods_id) throws Exception;

    List<Attrs> findGoodsAttr(Integer goods_id) throws Exception;

    Goods findGoodsById(Integer id) throws Exception;

    List<GoodsPics> findGoodsPicById(Integer id) throws Exception;

    List<Attrs> findGoodsAttrById(Integer id) throws Exception;

    void editGoodsById(AddGoods goods) throws Exception;

    void editPicsById(Integer id, String pics) throws Exception;

    void editAttrsById(GoodsAttr attr) throws Exception;

    void deleteGoodsById(Integer id) throws Exception;

    void savePics(String picsUrl) throws Exception;

    Integer findGoodsNum() throws Exception;

    List<Goods> findByName(String query, String pagenum, Integer pagesize) throws Exception;

    Integer findTotalByName(String query) throws Exception;
}
