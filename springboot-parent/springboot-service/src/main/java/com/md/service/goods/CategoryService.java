package com.md.service.goods;

import com.md.pojo.goods.Attribute;
import com.md.pojo.goods.Category;
import com.md.pojo.goods.ChildrenCats;

import java.util.List;

public interface CategoryService {
    List<ChildrenCats> findAllCats() throws Exception;

    Category addCategories(Integer cat_pid, String cat_name, Integer cat_level) throws Exception;

    Category findCatsById(Integer id) throws Exception;

    Category editCatsById(Integer id,String cat_name) throws Exception;

    void deleteCatsById(Integer id) throws Exception;

    List<Attribute> findAllCatAttrById(Integer id) throws Exception;

    Attribute addCatAttr(Attribute attribute) throws Exception;

    void deleteCatAttr(Integer id, Integer attrId) throws Exception;

    Attribute findCatAttrById(Integer attrId) throws Exception;

    Attribute editCatAttrById(Attribute attribute) throws Exception;
}
