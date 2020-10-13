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
    public List<ChildrenCats> findAllCats(Integer type, Integer pagenum, Integer pagesize) throws Exception {
        int i = pagesize * (pagenum - 1);// 从哪儿开始查
        List<ChildrenCats> catsParent = categoryDao.findFirstCats(i,pagesize);// 查出最顶层的分类
//      分层嵌套
        for (ChildrenCats parentCats : catsParent) {
            List<ChildrenCats> catsTwo = categoryDao.findCats(1, parentCats.getCat_id());// 查出第二层的分类
            parentCats.setChildren(catsTwo);
            for (ChildrenCats childrenCats : catsTwo) {
                List<ChildrenCats> catsThree = categoryDao.findCats(2, childrenCats.getCat_id());// 查出第三层的分类
                childrenCats.setChildren(catsThree);
            }
        }

        return catsParent;
    }

    @Override
    public Category addCategories(Integer cat_pid, String cat_name, Integer cat_level) throws Exception {
        categoryDao.addCategories(cat_pid,cat_name,cat_level);
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
    public List<Attribute> findAllCatAttrById(Integer id,String sel) throws Exception {
        List<Attribute> list = categoryDao.findAllCatAttrById(id,sel);
        return list;
    }

    @Override
    public Attribute addCatAttr(Integer cat_id,String attr_name,String sel) throws Exception {
        String attr_write = null;
        if ("many".equals(sel)) {
            attr_write = "list";
        }
        if ("only".equals(sel)) {
            attr_write = "manual";
        }
        categoryDao.addCatAttr(cat_id,attr_name,sel,attr_write);
        Attribute attr = categoryDao.findCatAttrByName(cat_id,attr_name,sel);
        return attr;
    }

    @Override
    public void deleteCatAttr(Integer id, Integer attrId) throws Exception {
        categoryDao.deleteCatAttrById(id,attrId);
    }

    @Override
    public Attribute findCatAttrById(Integer attrId) throws Exception {
        return categoryDao.findCatAttrById(attrId);
    }

    @Override
    public Attribute editCatAttrById(Integer id, Integer attrId, String attr_name, String attr_sel) throws Exception {
        categoryDao.editCatAttrById(id,attrId,attr_name,attr_sel);
        Attribute attr = categoryDao.findCatAttrById(attrId);
        return attr;
    }

    @Override
    public Integer findTotalCats() throws Exception {
        Integer total = categoryDao.findTotalCats();
        return total;
    }
}
