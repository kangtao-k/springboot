package com.md.service.goods.impl;

import com.md.dao.CategoryDao;
import com.md.pojo.goods.Attribute;
import com.md.pojo.goods.Category;
import com.md.pojo.goods.ChildrenCats;
import com.md.service.goods.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<ChildrenCats> findAllCats() throws Exception{
        List<ChildrenCats> catsParent = categoryDao.findFirstCats();// 查出最顶层的分类
        List<ChildrenCats> catsTwo = categoryDao.findSecondCats();// 查出第二层的分类
        List<ChildrenCats> catsThree = categoryDao.findThirdCats();// 查出第三层的分类

        List<ChildrenCats> cateListThree = new ArrayList<ChildrenCats>();// 放置三级分类
        List<ChildrenCats> cateListTwo = new ArrayList<ChildrenCats>();// 放置二级分类

//        将三级分类放入二级分类下
        for (ChildrenCats parentCats : catsTwo) {
            for (ChildrenCats childrenCats : catsThree) {
                if (parentCats.getCat_id().equals(childrenCats.getCat_pid())) {
                    cateListThree.add(childrenCats);
                }
            }
            parentCats.setChildren(cateListThree);
        }
//        将二级分类放入顶级分类下
        for (ChildrenCats parentCats : catsParent) {
            for (ChildrenCats childrenCats : catsTwo) {
                if (parentCats.getCat_id().equals(childrenCats.getCat_pid())) {
                    cateListTwo.add(childrenCats);
                }
            }
            parentCats.setChildren(cateListTwo);
        }
        return catsParent;
    }

    @Override
    public Category addCategories(Integer cat_pid, String cat_name, Integer cat_level) throws Exception {
        categoryDao.addCategories(cat_pid,cat_name,cat_level,0);
//        查询刚刚插入的数据的id
        Integer cateId = categoryDao.findCateIdByName(cat_name);
        Category category = categoryDao.findCatsById(cateId);
        return category;
    }

    @Override
    public Category findCatsById(Integer id) throws Exception {
        Category category = categoryDao.findCatsById(id);
        return category;
    }

    @Override
    public Category editCatsById(Integer id,String cat_name) throws Exception {
        categoryDao.editCatsById(id,cat_name);// 编辑提交的分类
//        查询刚提交的分类
        Category category = categoryDao.findCatsById(id);
        return category;
    }

    @Override
    public void deleteCatsById(Integer id) throws Exception {
        categoryDao.deleteCatsById(id);
    }

    @Override
    public List<Attribute> findAllCatAttrById(Integer id) throws Exception {
        List<Attribute> list = categoryDao.findAllCatAttrById(id);
        return list;
    }

    @Override
    public Attribute addCatAttr(Attribute attribute) throws Exception {
        categoryDao.addCatAttr(attribute);
        Attribute attrs = categoryDao.findCatAttrByName(attribute.getAttr_name());
        Attribute attr = categoryDao.findCatAttrById(attrs.getAttr_id());
        return attr;
    }

    @Override
    public void deleteCatAttr(Integer id, Integer attrId) throws Exception {
        categoryDao.deleteCatAttrById(attrId);
        categoryDao.deleteCatsById(id);
    }

    @Override
    public Attribute findCatAttrById(Integer attrId) throws Exception {
        return categoryDao.findCatAttrById(attrId);
    }

    @Override
    public Attribute editCatAttrById(Attribute attribute) throws Exception {
        categoryDao.editCatAttrById(attribute);
        Attribute attr = categoryDao.findCatAttrById(attribute.getAttr_id());
        return attr;
    }
}
