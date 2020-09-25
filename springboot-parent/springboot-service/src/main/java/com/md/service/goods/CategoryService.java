package com.md.service.goods;

import com.md.pojo.goods.Attribute;
import com.md.pojo.goods.Category;
import com.md.pojo.goods.ChildrenCats;

import java.util.List;

public interface CategoryService {
    List<ChildrenCats> findAllCats(Integer type,Integer pagenum,Integer pagesize) throws Exception;

    Category addCategories(Integer cat_pid, String cat_name, Integer cat_level) throws Exception;

    Category findCatsById(Integer id) throws Exception;

    Category editCatsById(Integer id,String cat_name) throws Exception;

    void deleteCatsById(Integer id) throws Exception;

    List<Attribute> findAllCatAttrById(Integer id,String sel) throws Exception;

    Attribute addCatAttr(Integer id,String attr_name,String attr_sel) throws Exception;

    void deleteCatAttr(Integer id, Integer attrId) throws Exception;

    Attribute findCatAttrById(Integer attrId) throws Exception;

    Integer findTotalCats() throws Exception;

    Attribute editCatAttrById(Integer id, Integer attrId, String attr_name, String attr_sel) throws Exception;
}
