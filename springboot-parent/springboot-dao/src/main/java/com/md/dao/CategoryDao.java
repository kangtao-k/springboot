package com.md.dao;

import com.md.pojo.goods.Attribute;
import com.md.pojo.goods.Category;
import com.md.pojo.goods.ChildrenCats;
import org.apache.ibatis.annotations.*;

import javax.persistence.MapKey;
import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryDao {

    @Select("select * from sp_category where cat_level=0 limit #{i},#{pagesize}")
    List<ChildrenCats> findFirstCats(int i, Integer pagesize);

    @Select("select * from sp_category where cat_level=#{level} and cat_pid=#{pid}")
    List<ChildrenCats> findCats(Integer level,Integer pid);

    @Insert("insert into sp_category values(null,#{cat_name},#{cat_pid},#{cat_level},#{i},null,null)")
    void addCategories(Integer cat_pid, String cat_name, Integer cat_level, int i);

    @Select("select cat_id from sp_category where cat_name=#{cat_name}")
    Integer findCateIdByName(String cat_name);

    @Select("select * from sp_category where cat_id=#{cateId}")
    Category findCatsById(Integer cateId);

    @Update("update sp_category set cat_name=#{cat_name} where cat_id=#{id}")
    void editCatsById(Integer id,String cat_name);

    @Delete("delete from sp_category where cat_id=#{id}")
    void deleteCatsById(Integer id);

    @Select("select * from sp_attribute where cat_id=#{id} and attr_sel=#{sel}")
    List<Attribute> findAllCatAttrById(Integer id,String sel);

    @Select("select * from sp_attribute where attr_name=#{attr_name} and cat_id=#{cat_id} and attr_sel=#{sel}")
    Attribute findCatAttrByName(Integer cat_id,String attr_name,String sel);

    @Select("select * from sp_attribute where attr_id=#{attrId}")
    Attribute findCatAttrById(Integer attrId);

    @Delete("delete from sp_attribute where attr_id=#{attrId}")
    void deleteCatAttrById(Integer id, Integer attrId);

    @Update("update sp_attribute set attr_name=#{attr_name},cat_id=#{id},attr_sel=#{attr_sel} where attr_id=#{attrId}")
    void editCatAttrById(Integer id, Integer attrId, String attr_name, String attr_sel);

    @Select("select count(*) from sp_category where cat_level=0")
    Integer findTotalCats();

    @Insert("insert into sp_attribute values(null,#{attr_name},#{cat_id},#{sel},#{attr_write},'支持',null)")
    void addCatAttr(Integer cat_id, String attr_name, String sel,String attr_write);
}
