package com.md.dao;

import com.md.pojo.goods.Attribute;
import com.md.pojo.goods.Category;
import com.md.pojo.goods.ChildrenCats;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryDao {

    @Select("select * from sp_category where cat_level=0 limit #{i},#{pagesize}")
    List<ChildrenCats> findFirstCats(@Param("i") int i, @Param("pagesize") Integer pagesize);

    @Select("select * from sp_category where cat_level=#{level} and cat_pid=#{pid}")
    List<ChildrenCats> findCats(@Param("level") Integer level, @Param("pid") Integer pid);

    @Insert("insert into sp_category values(null,#{cat_name},#{cat_pid},#{cat_level},#{i},null,null)")
    void addCategories(@Param("cat_pid") Integer cat_pid, @Param("cat_name") String cat_name,
                       @Param("cat_level") Integer cat_level, int i);

    @Select("select cat_id from sp_category where cat_name=#{cat_name}")
    Integer findCateIdByName(@Param("cat_name") String cat_name);

    @Select("select * from sp_category where cat_id=#{cateId}")
    Category findCatsById(@Param("cateId") Integer cateId);

    @Update("update sp_category set cat_name=#{cat_name} where cat_id=#{id}")
    void editCatsById(@Param("id") Integer id, @Param("cat_name") String cat_name);

    @Delete("delete from sp_category where cat_id=#{id}")
    void deleteCatsById(@Param("id") Integer id);

    @Select("select * from sp_attribute where cat_id=#{id} and attr_sel=#{sel}")
    List<Attribute> findAllCatAttrById(@Param("id") Integer id, @Param("sel") String sel);

    @Select("select * from sp_attribute where attr_name=#{attr_name} and cat_id=#{cat_id} and attr_sel=#{sel}")
    Attribute findCatAttrByName(@Param("cat_id") Integer cat_id, @Param("attr_name") String attr_name,
                                @Param("sel") String sel);

    @Select("select * from sp_attribute where attr_id=#{attrId}")
    Attribute findCatAttrById(@Param("attrId") Integer attrId);

    @Delete("delete from sp_attribute where attr_id=#{attrId}")
    void deleteCatAttrById(@Param("id") Integer id, @Param("attrId") Integer attrId);

    @Update("update sp_attribute set attr_name=#{attr_name},cat_id=#{id},attr_sel=#{attr_sel} where attr_id=#{attrId}")
    void editCatAttrById(@Param("id") Integer id, @Param("attrId") Integer attrId, @Param("attr_name") String attr_name,
                         @Param("attr_sel") String attr_sel);

    @Select("select count(*) from sp_category where cat_level=0")
    Integer findTotalCats();

    @Insert("insert into sp_attribute values(null,#{attr_name},#{cat_id},#{sel},#{attr_write},'支持',null)")
    void addCatAttr(@Param("cat_id") Integer cat_id, @Param("attr_name") String attr_name,
                    @Param("sel") String sel, @Param("attr_write") String attr_write);
}
