package com.md.controller;

import com.md.entity.bo.AddGoods;
import com.md.entity.other.Attrs;
import com.md.entity.other.Meta;
import com.md.entity.goodsVo.PageResult;
import com.md.entity.goodsVo.Result;
import com.md.pojo.goods.Goods;
import com.md.pojo.goods.GoodsAttr;
import com.md.pojo.goods.GoodsPics;
import com.md.service.goods.SpGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController//标明这是一个SpringMVC的Controller控制器
//@SpringBootApplication//Spring Boot项目的核心注解，主要目的是开启自动配置。
//SpringBootApplication:包含@Configuration、@EnableAutoConfiguration、@ComponentScan
public class SpGoodsApplication {

    @Autowired
    private SpGoodsService goodsService;
    private Meta meta = Meta.succ("获取成功");// 返回成功消息

    /**
     * 查询全部产品
     *
     * @return
     */
    @GetMapping(value = "/goods")
    public Result findAllGoods(@RequestParam String pagenum,
                               @RequestParam Integer pagesize) throws Exception {
        Long total = goodsService.findGoodsNum();
        Integer num = Integer.parseInt(pagenum);
        List<Goods> skus = goodsService.findAll(num, pagesize);// 分页查询全部产品
        PageResult result = PageResult.succ(total, pagenum, skus);// 返回当前页码和总页数
        Result r = Result.succ(meta, result);// 封装
        return r;
    }

    /**
     * 添加商品
     *
     * @param goods
     * @throws Exception
     */
    @PostMapping(value = "/goods")
    @ResponseBody
    public Result addGoods(@RequestBody AddGoods goods) throws Exception {
//        添加商品
        Goods findGoods = goodsService.addGoods(goods);
//        根据商品id查询商品属性
        Integer goods_id = findGoods.getGoods_id();
        List<GoodsPics> goodsPics = goodsService.findGoodsPics(goods_id);
//        根据商品id查询商品图片
        List<Attrs> goodsAttrs = goodsService.findGoodsAttr(goods_id);
        Long total = goodsService.findGoodsNum();
        findGoods.setPics(goodsPics);
        findGoods.setAttrs(goodsAttrs);
        List<Goods> list = new ArrayList<>();
        PageResult result = PageResult.succ(total, "1", list);
        Result r = Result.succ(meta, result);
        return r;
    }

    /**
     * 根据id查询商品
     *
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/goods/{id}")
    public Result findGoodsById(@PathVariable Integer id) throws Exception {
        return findByIdGoods(id);
    }

    /**
     * 编辑提交商品
     *
     * @param id
     * @param goods
     * @return
     * @throws Exception
     */
    @PutMapping(value = "/goods/{id}")
    public Result editGoodsById(@PathVariable Integer id, @RequestBody AddGoods goods) throws Exception {
//        编辑商品
        goods.setGoods_id(id);
        goodsService.editGoodsById(goods);
        ArrayList<HashMap<String, String>> pics = goods.getPics();
        for (HashMap<String, String> pic : pics) {
            Iterator<String> iterator = pic.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = pic.get(key);
                goodsService.editPicsById(id, value);
            }
        }
        GoodsAttr goodsAttr = new GoodsAttr();
        goodsAttr.setGoods_id(id);
        ArrayList<HashMap<String, String>> attrs = goods.getAttrs();
        for (HashMap<String, String> attr : attrs) {
            for (String key : attr.keySet()) {
                String value = attr.get(key);
                if ("attr_id".equals(key)) {
                    goodsAttr.setAttr_id(Integer.parseInt(value));
                }
                if ("attr_value".equals(key)) {
                    goodsAttr.setAttr_value(value);
                }
                goodsService.editAttrsById(goodsAttr);
            }
        }
//        返回商品查询
        Result r = findByIdGoods(id);
        return r;
    }

    /**
     * 删除商品
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/goods/{id}")
    public Result deleteGoodsById(@PathVariable(required = true) Integer id) throws Exception {
        goodsService.deleteGoodsById(id);
        Result result = Result.succ(meta, null);
        return result;
    }

    /**
     * 图片上传
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/upload")
    public Result uploadPics(@RequestParam MultipartFile file, HttpServletRequest request) throws Exception {

        //上传文件的位置,默认会在项目根目录找static文件夹,需手动创建,不然找到是临时路径。
        String path = request.getSession().getServletContext().getRealPath("/tmp_uploads/");
        //判断该路径是否存在
        File pFile = new File(path);
        // 若不存在则创建该文件夹
        if(!pFile.exists()){
            pFile.mkdirs();
        }

        if (file.isEmpty()) {
            return Result.failed("上传失败");
        }
        String filename = file.getOriginalFilename();// 获取图片名称
        //String filePath = "tmp_uploads\\";// 图片存储路径
        String fileUrl = path + filename;
        File dest = new File(fileUrl);
        try {
            file.transferTo(dest);
            String picsUrl = "http://www.kangkangwt.cn:8088/" + fileUrl;
            goodsService.savePics(picsUrl);
            Map<String, String> map = new HashMap<String, String>();
            map.put("tmp_path", fileUrl);
            map.put("url", picsUrl);
            return Result.succ(meta, map);
        } catch (IOException e) {
//            throw new Exception(e);
            return Result.failed("上传失败");
        }
    }

    /**
     * 根据id查询商品
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Result findByIdGoods(Integer id) throws Exception {
        Goods goods = goodsService.findGoodsById(id);
        List<GoodsPics> goodsPics = goodsService.findGoodsPicById(id);
        List<Attrs> goodsAttrs = goodsService.findGoodsAttrById(id);
        goods.setPics(goodsPics);
        goods.setAttrs(goodsAttrs);
        Result r = Result.succ(meta, goods);
        return r;
    }

}
