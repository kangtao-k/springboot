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

    @Select("select * from sp_attribute where cat_id=#{id}")
    List<Attribute> findAllCatAttrById(Integer id);

    @Insert("insert into sp_attribute values(null,#{attr_name},#{cat_id},#{attr_sel},#{attr_write},#{attr_vals},#{delete_time})")
    void addCatAttr(Attribute attribute);

    @Select("select * from sp_attribute where attr_name=#{attr_name}")
    Attribute findCatAttrByName(String attr_name);

    @Select("select * from sp_attribute where attr_id=#{attrId}")
    Attribute findCatAttrById(Integer attrId);

    @Delete("delete from sp_attribute where attr_id=#{attrId}")
    void deleteCatAttrById(Integer attrId);

    @Update("update sp_attribute set attr_name=#{attr_name},cat_id=#{cat_id},attr_write=#{attr_write}," +
            "attr_sel=#{attr_sel},attr_vals=#{attr_vals} where attr_id=#{attr_id}")
    void editCatAttrById(Attribute attribute);

    @Select("select count(*) from sp_category where cat_level=0")
    Integer findTotalCats();
}
