package com.md.service.goods.impl;

import com.md.dao.SpGoodsDao;
import com.md.entity.bo.AddGoods;
import com.md.entity.other.Attrs;
import com.md.pojo.goods.Goods;
import com.md.pojo.goods.GoodsAttr;
import com.md.pojo.goods.GoodsPics;
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
        return goodsDao.findAll(a,pageSize);
    }

    @Override
    public Goods addGoods(AddGoods goods) throws Exception {
        Goods iGoods = new Goods();
        iGoods.setGoods_name(goods.getGoods_name());
        iGoods.setGoods_price(goods.getGoods_price());
        iGoods.setGoods_number(goods.getGoods_number());
        iGoods.setGoods_introduce(goods.getGoods_introduce());
        iGoods.setGoods_weight(goods.getGoods_weight());
        String[] cats = goods.getGoods_cat().split(",");
        iGoods.setCat_one_id(Integer.parseInt(cats[0]));
        iGoods.setCat_two_id(Integer.parseInt(cats[1]));
        iGoods.setCat_three_id(Integer.parseInt(cats[2]));

        Integer addtime = -(int) System.currentTimeMillis();
        iGoods.setAdd_time(addtime);
        iGoods.setUpd_time(addtime);
        goodsDao.addGoods(iGoods);

        GoodsAttr attr = new GoodsAttr();
        GoodsPics pics = new GoodsPics();
//        获取商品属性
        ArrayList<HashMap<String, String>> attrs = goods.getAttrs();
        for (HashMap<String, String> map : attrs) {
            Set<String> strings = map.keySet();
            for (String key : strings) {
                if ("attr_id".equals(key)) {
                    String value = map.get(key);
                    attr.setAttr_id(Integer.parseInt(value));
                }
                if ("attr_value".equals(key)) {
                    String value = map.get(key);
                    attr.setAttr_value(value);
                }
            }
        }
//        查询刚刚插入的商品的商品id
        Integer goodsIdByName = goodsDao.findGoodsIdByName(goods.getGoods_name());
        attr.setGoods_id(goodsIdByName);// 将id放入商品属性表中
        pics.setGoods_id(goodsIdByName);// 将id放入商品图片表中
//        获取商品图片
        ArrayList<HashMap<String, String>> goodsPics = goods.getPics();
        for (HashMap<String, String> goodsPic : goodsPics) {
            Set<String> set = goodsPic.keySet();
            for (String key : set) {
                if ("pic".equals(key)) {
                    pics.setPics_big(goodsPic.get(key));
                    pics.setPics_mid(goodsPic.get(key));
                    pics.setPics_sma(goodsPic.get(key));
                }
                if ("pic_big".equals(key)) {
                    pics.setPics_big(goodsPic.get(key));
                }
                if ("pic_mid".equals(key)) {
                    pics.setPics_mid(goodsPic.get(key));
                }
                if ("pic_sma".equals(key)) {
                    pics.setPics_sma(goodsPic.get(key));
                }
            }
        }
        goodsDao.addGoodsPics(pics);
        goodsDao.addGoodsAttr(attr);
//        返回刚刚查询的商品
        Goods goodsNow = goodsDao.findGoodsById(goodsIdByName);
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
    public Long findGoodsNum() throws Exception {
        return goodsDao.findGoodsNum();
    }

}
