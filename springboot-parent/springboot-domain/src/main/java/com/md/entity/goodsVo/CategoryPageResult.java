package com.md.entity.goodsVo;

import com.md.pojo.goods.ChildrenCats;
import lombok.Data;

import java.util.List;

@Data
public class CategoryPageResult {
    private Integer total;// 总记录数
    private Integer pagenum;// 当前页
    private Integer pagesize;// 每页显示条数
    private List<ChildrenCats> result;// 分类结果集

    public static CategoryPageResult succ(Integer total, Integer pagenum, Integer pagesize, List<ChildrenCats> result) {
        CategoryPageResult pageResult = new CategoryPageResult();
        pageResult.setTotal(total);
        pageResult.setPagenum(pagenum);
        pageResult.setPagesize(pagesize);
        pageResult.setResult(result);
        return pageResult;
    }
}
