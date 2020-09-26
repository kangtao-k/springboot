package com.md.dao;

import com.md.entity.bo.AddGoods;
import com.md.entity.other.Attrs;
import com.md.pojo.goods.Goods;
import com.md.pojo.goods.GoodsAttr;
import com.md.pojo.goods.GoodsPics;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SpGoodsDao {

    //    查询所有的产品信息
    @Select("select * from sp_goods limit #{a},#{pageSize}")
    public List<Goods> findAll(@Param("a") int a, @Param("pageSize") Integer pageSize) throws Exception;

    @Insert("insert into sp_goods(goods_id,goods_name,goods_price,goods_number,goods_weight,cat_one_id,cat_two_id,cat_three_id," +
            "goods_introduce,add_time,upd_time,goods_big_logo,goods_small_logo,is_del) " +
            "values(null,#{iGoods.goods_name},#{iGoods.goods_price},#{iGoods.goods_number},#{iGoods.goods_weight}," +
            "#{iGoods.cat_one_id},#{iGoods.cat_two_id},#{iGoods.cat_three_id},#{iGoods.goods_introduce},#{iGoods.add_time}," +
            "#{iGoods.upd_time},null,null,0)")
    void addGoods(@Param("iGoods") Goods iGoods);

    @Select("select goods_id from sp_goods where goods_name = #{goods_name}")
    Integer findGoodsIdByName(@Param("goods_name") String goods_name);

    @Insert("insert into sp_goods_pics values(null,#{goodsId},#{pic},#{pic},#{pic})")
    void addGoodsPics(@Param("goodsId") Integer goodsId,@Param("pic") String pic);

    @Select("select * from sp_goods_pics where goods_id=#{goods_id}")
    List<GoodsPics> findGoodsPics(@Param("goods_id") Integer goods_id);

    @Select("SELECT goods_id,A.`attr_id`,attr_value,add_price,attr_name,attr_sel,attr_write,attr_vals FROM" +
            " sp_goods_attr A LEFT JOIN sp_attribute B ON A.`attr_id`=B.`attr_id` where goods_id=#{goods_id};")
    List<Attrs> findGoodsAttr(@Param("goods_id") Integer goods_id);

    @Select("select * from sp_goods where goods_id = #{goods_id}")
    Goods findGoodsById(@Param("goods_id") Integer goods_id);

    @Select("select * from sp_goods_pics where goods_id = #{goods_id}")
    List<GoodsPics> findGoodsPicById(@Param("goods_id") Integer goods_id);

    @Select("select * from sp_goods_attr where goods_id = #{goods_id}")
    List<Attrs> findGoodsAttrById(@Param("goods_id") Integer goods_id);

    @Update("update sp_goods set goods_name=#{goods.goods_name},goods_price=#{goods.goods_price},goods_number=#{goods.goods_number}," +
            "goods_weight=#{goods.goods_weight},goods_introduce=#{goods.goods_introduce} where goods_id=#{goods.goods_id}")
    void editGoodsById(@Param("goods") AddGoods goods);

    @Update("update sp_goods_pics set pics_big=#{pics},pics_mid=#{pics},pics_sma=#{pics} where goods_id=#{goods_id}")
    void editPicsById(@Param("goods_id") Integer goods_id, @Param("pics") String pics);

    @Update("update sp_goods_attr set attr_value=#{attr.attr_value} where goods_id=#{attr.id} and attr_id=#{attr.attr_id}")
    void editAttrsById(@Param("attr") GoodsAttr attr);

    @Delete("delete from sp_goods where goods_id=#{id}")
    void deleteGoodsById(@Param("id") Integer id);

    @Delete("delete from sp_goods_pics where goods_id=#{id}")
    void deletePicsById(@Param("id") Integer id);

    @Delete("delete from sp_goods_attr where goods_id=#{id}")
    void deleteAttrById(@Param("id") Integer id);

    @Insert("insert into sp_goods_pics values(null,1,#{picsUrl},#{picsUrl},#{picsUrl})")
    void savePics(@Param("picsUrl") String picsUrl);

    @Select("select count(*) from sp_goods")
    Long findGoodsNum();

    @Insert("insert into sp_goods_attr values(null,#{goodsId},#{attr_id},#{attr_value},0.00)")
    void addGoodsAttrByGoodsId(@Param("attr_id") Integer attr_id,@Param("attr_value") String attr_value,
                               @Param("goodsId") Integer goodsId);

    @Select("select * from sp_goods where goods_name like '%${query}%' limit #{a},#{pagesize}")
    List<Goods> findByLiskeName(@Param("query") String query, @Param("pagesize") Integer pagesize,@Param("a") Integer a) throws Exception;
}
