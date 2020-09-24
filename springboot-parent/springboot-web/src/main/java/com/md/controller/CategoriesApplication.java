package com.md.controller;

import com.md.entity.goodsVo.CategoryPageResult;
import com.md.entity.other.Meta;
import com.md.entity.goodsVo.Result;
import com.md.pojo.goods.Attribute;
import com.md.pojo.goods.Category;
import com.md.pojo.goods.ChildrenCats;
import com.md.service.goods.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoriesApplication {

    @Autowired
    private CategoryService categoryService;
    private Meta meta = Meta.succ("成功");

    /**
     * 查询商品分类数据列表
     * @return
     * @throws Exception
     */
    @GetMapping("/categories")
    public Result findAllCats(@RequestParam Integer type,@RequestParam Integer pagenum,
                              @RequestParam Integer pagesize) throws Exception{
        List<ChildrenCats> allCats = categoryService.findAllCats(type,pagenum,pagesize);
        Integer total = categoryService.findTotalCats();
        CategoryPageResult pageResult = CategoryPageResult.succ(total, pagenum - 1, pagesize, allCats);
        Result result = Result.succ(meta, pageResult);
        return result;
    }

    /**
     * 添加商品分类
     * @return
     * @throws Exception
     */
    @PostMapping("/categories")
    public Result addCategories(@RequestParam(required = true) Integer cat_pid,
                                @RequestParam(required = true) String cat_name,
                                @RequestParam(required = true) Integer cat_level) throws Exception{
        Category categories = categoryService.addCategories(cat_pid,cat_name,cat_level);
        Result result = Result.succ(meta, categories);
        return result;
    }

    /**
     * 根据id查询分类
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/categories/{id}")
    public Result findCatsById(@PathVariable(required = true) Integer id) throws Exception{
        Category category = categoryService.findCatsById(id);
        Result result = Result.succ(meta, category);
        return result;
    }

    /**
     * 编辑提交分类
     * @param id
     * @return
     * @throws Exception
     */
    @PutMapping("/categories/{id}")
    public Result editCatsById(@PathVariable(required = true) Integer id,
                               @RequestParam(required = true) String cat_name) throws Exception{
        Category category = categoryService.editCatsById(id,cat_name);
        Result result = Result.succ(meta, category);
        return result;
    }

    /**
     * 删除分类
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping("/categories/{id}")
    public Result deleteCatsById(@PathVariable(required = true) Integer id) throws Exception{
        categoryService.deleteCatsById(id);
        Result result = Result.succ(meta, null);
        return result;
    }

    /**
     * 查询分类参数列表
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/categories/{id}/attributes")
    public Result findCatAttrById(@PathVariable(required = true) Integer id,
                                  @RequestParam String attr_sel) throws Exception{
        List<Attribute> list = categoryService.findAllCatAttrById(id);
        Result result = Result.succ(meta, list);
        return result;
    }

    /**
     * 添加动态参数或者静态属性
     * @param id 分类id（不是主键）
     * @return
     * @throws Exception
     */
    @PostMapping("/categories/{id}/attributes")
    public Result addCatAttr(@PathVariable Integer id,
                             @RequestParam(required = true) String attr_name,
                             @RequestParam(required = true) String attr_sel,
                             @RequestParam(required = false) String attr_vals) throws Exception{
        Attribute attribute = new Attribute();
        attribute.setCat_id(id);
        attribute.setAttr_name(attr_name);
        attribute.setAttr_sel(attr_sel);
        attribute.setAttr_vals(attr_vals);
        attribute.setAttr_write("list");
        Attribute attr = categoryService.addCatAttr(attribute);
        Result result = Result.succ(meta, attr);
        return result;
    }

    /**
     * 删除参数
     * @param id 分类id
     * @param attrid 属性参数id
     * @return
     * @throws Exception
     */
    @DeleteMapping("/categories/{id}/attributes/{attrid}")
    public Result deleteCatAttr(@PathVariable Integer id,@PathVariable Integer attrid) throws Exception{
        categoryService.deleteCatAttr(id,attrid);
        Result result = Result.succ(meta, null);
        return result;
    }

    /**
     * 根据id查询参数
     * @param id
     * @param attrId
     * @param attr_sel
     * @param attr_vals
     * @return
     * @throws Exception
     */
    @GetMapping("/categories/{id}/attributes/{attrId}")
    public Result findCatAttrById(@PathVariable Integer id,@PathVariable Integer attrId,
                                  @RequestParam String attr_sel,
                                  @RequestParam(required = false) String attr_vals) throws Exception{
        Attribute attribute = categoryService.findCatAttrById(attrId);
        Result result = Result.succ(meta, attribute);
        return result;
    }

    /**
     * 编辑提交参数
     * @param id
     * @param attrId
     * @param attr_name
     * @param attr_sel
     * @param attr_vals
     * @return
     * @throws Exception
     */
    @PutMapping("/categories/{id}/attributes/{attrId}")
    public Result editCatAttrById(@PathVariable Integer id,@PathVariable Integer attrId,
                                  @RequestParam String attr_name,@RequestParam String attr_sel,
                                  @RequestParam(required = false) String attr_vals) throws Exception{
        Attribute attribute = new Attribute();
        attribute.setCat_id(id);
        attribute.setAttr_id(attrId);
        attribute.setAttr_name(attr_name);
        attribute.setAttr_sel(attr_sel);
        attribute.setAttr_write("manual");
        attribute.setAttr_vals(attr_vals);
        Attribute attr = categoryService.editCatAttrById(attribute);
        Result result = Result.succ(meta, attr);
        return result;
    }
}
