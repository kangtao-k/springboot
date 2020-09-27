package com.md.service.goods.impl;

import com.md.dao.SpGoodsDao;
import com.md.entity.bo.AddGoods;
import com.md.entity.other.AddAttrs;
import com.md.entity.other.Attrs;
import com.md.pojo.goods.Goods;
import com.md.pojo.goods.GoodsAttr;
import com.md.pojo.goods.GoodsPics;
import com.md.pojo.goods.Goods_2;
import com.md.service.goods.SpGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional// 事务性
public class SpGoodsServiceImpl implements SpGoodsService {

    @Autowired
    private SpGoodsDao goodsDao;

    @Override
    public List<Goods> findAll(Integer pageNum, Integer pageSize) throws Exception {
        int a = pageSize * (pageNum - 1);// 开始查询值
        List<Goods> list = goodsDao.findAll(a, pageSize);
        return list;
    }

    @Override
    public Goods addGoods(AddGoods goods) throws Exception {
        Goods iGoods = new Goods();
        iGoods.setGoods_name(goods.getGoods_name());
        iGoods.setGoods_price(Integer.parseInt(goods.getGoods_price()));
        iGoods.setGoods_number(Integer.parseInt(goods.getGoods_number()));
        iGoods.setGoods_introduce(goods.getGoods_introduce());
        iGoods.setGoods_weight(Integer.parseInt(goods.getGoods_weight()));
        String[] cats = goods.getGoods_cat().split(",");
        iGoods.setCat_one_id(Integer.parseInt(cats[0]));
        iGoods.setCat_two_id(Integer.parseInt(cats[1]));
        iGoods.setCat_three_id(Integer.parseInt(cats[2]));

        Integer addtime = -(int) System.currentTimeMillis();
        iGoods.setAdd_time(addtime);
        iGoods.setUpd_time(addtime);
        goodsDao.addGoods(iGoods);
        Integer goodsId = goodsDao.findGoodsIdByName(goods.getGoods_name());

//        添加商品属性
        ArrayList<AddAttrs> attrs = goods.getAttrs();
        for (AddAttrs attr : attrs) {
            goodsDao.addGoodsAttrByGoodsId(attr.getAttr_id(),attr.getAttr_value(),goodsId);
        }

//        添加图片
        ArrayList<HashMap<String, String>> goodsPics = goods.getPics();
        for (HashMap<String, String> pics : goodsPics) {
            String pic = pics.get("pic");
            goodsDao.addGoodsPics(goodsId,pic);
        }
//        返回刚刚添加的商品
        Goods goodsNow = goodsDao.findGoodsById(goodsId);
        return goodsNow;
    }

    @Override
    public List<GoodsPics> findGoodsPics(Integer goods_id) throws Exception {
        return goodsDao.findGoodsPics(goods_id);
    }

    @Override
    public List<Attrs> findGoodsAttr(Integer goods_id) throws Exception {
        return goodsDao.findGoodsAttr(goods_id);
    }

    /**
     * 根据商品id查询商品信息
     *
     * @param goods_id
     * @return
     */
    @Override
    public Goods findGoodsById(Integer goods_id) throws Exception {
        return goodsDao.findGoodsById(goods_id);
    }

    /**
     * 根据id查询商品图片
     *
     * @param goods_id
     * @return
     */
    @Override
    public List<GoodsPics> findGoodsPicById(Integer goods_id) throws Exception {
        return goodsDao.findGoodsPicById(goods_id);
    }

    /**
     * 根据商品id查询商品图片
     *
     * @param goods_id
     * @return
     */
    @Override
    public List<Attrs> findGoodsAttrById(Integer goods_id) throws Exception {
        return goodsDao.findGoodsAttrById(goods_id);
    }

    @Override
    public void editGoodsById(AddGoods goods) throws Exception {
        goodsDao.editGoodsById(goods);
    }

    @Override
    public void editPicsById(Integer id, String pics) throws Exception {
        goodsDao.editPicsById(id, pics);
    }

    @Override
    public void editAttrsById(GoodsAttr attr) throws Exception {
        goodsDao.editAttrsById(attr);
    }

    @Override
    public void deleteGoodsById(Integer id) throws Exception {
        goodsDao.deleteGoodsById(id);
        goodsDao.deletePicsById(id);
        goodsDao.deleteAttrById(id);
    }

    @Override
    public void savePics(String picsUrl) throws Exception {
        goodsDao.savePics(picsUrl);
    }

    @Override
    public Integer findGoodsNum() throws Exception {
        return goodsDao.findGoodsNum();
    }

    @Override
    public List<Goods> findByName(String query, String pagenum, Integer pagesize) throws Exception {
        //计算开始查询的位置
        int a = pagesize*(Integer.parseInt(pagenum) -1);
        List<Goods> goods = goodsDao.findByLiskeName(query,a,pagesize);
        return goods;
    }

    @Override
    public Integer findTotalByName(String query) throws Exception {
        return goodsDao.findTotalByName(query);
    }

}
