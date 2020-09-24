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
    public List<Goods> findAll(int a,Integer pageSize) throws Exception;

    @Insert("insert into sp_goods(goods_name,goods_price,goods_number,goods_weight,cat_one_id,cat_two_id,cat_three_id," +
            "goods_introduce,add_time,upd_time) " +
            "values(#{goods_name},#{goods_price},#{goods_number},#{goods_weight},#{cat_one_id},#{cat_two_id}," +
            "#{cat_three_id},#{goods_introduce},#{add_time},#{upd_time})")
    void addGoods(Goods iGoods);

    @Insert("insert into sp_goods_attr values(null,#{goods_id},#{attr_id},#{attr_value},null)")
    void addGoodsAttr(GoodsAttr attr);

    @Select("select goods_id from sp_goods where goods_name = #{goods_name}")
    Integer findGoodsIdByName(String goods_name);

    @Insert("insert into sp_goods_pics values(null,#{goods_id},#{pics_big},#{pics_mid},#{pics_sma})")
    void addGoodsPics(GoodsPics pics);

    @Select("select * from sp_goods_pics where goods_id=#{goods_id}")
    List<GoodsPics> findGoodsPics(Integer goods_id);

    @Select("SELECT goods_id,A.`attr_id`,attr_value,add_price,attr_name,attr_sel,attr_write,attr_vals FROM" +
            " sp_goods_attr A LEFT JOIN sp_attribute B ON A.`attr_id`=B.`attr_id` where goods_id=#{goods_id};")
    List<Attrs> findGoodsAttr(Integer goods_id);

    @Select("select * from sp_goods where goods_id = #{goods_id}")
    Goods findGoodsById(Integer goods_id);

    @Select("select * from sp_goods_pics where goods_id = #{goods_id}")
    List<GoodsPics> findGoodsPicById(Integer goods_id);

    @Select("select * from sp_goods_attr where goods_id = #{goods_id}")
    List<Attrs> findGoodsAttrById(Integer goods_id);

    @Update("update sp_goods set goods_name=#{goods_name},goods_price=#{goods_price},goods_number=#{goods_number}," +
            "goods_weight=#{goods_weight},goods_introduce=#{goods_introduce} where goods_id=#{goods_id}")
    void editGoodsById(AddGoods goods);

    @Update("update sp_goods_pics set pics_big=#{pics},pics_mid=#{pics},pics_sma=#{pics} where goods_id=#{goods_id}")
    void editPicsById(Integer goods_id, String pics);

    @Update("update sp_goods_attr set attr_value=#{attr_value} where goods_id=#{id} and attr_id=#{attr_id}")
    void editAttrsById(GoodsAttr attr);

    @Delete("delete from sp_goods where goods_id=#{id}")
    void deleteGoodsById(Integer id);

    @Delete("delete from sp_goods_pics where goods_id=#{id}")
    void deletePicsById(Integer id);

    @Delete("delete from sp_goods_attr where goods_id=#{id}")
    void deleteAttrById(Integer id);

    @Insert("insert into sp_goods_pics values(null,1,#{picsUrl},#{picsUrl},#{picsUrl})")
    void savePics(String picsUrl);

    @Select("select count(*) from sp_goods")
    Long findGoodsNum();
}
