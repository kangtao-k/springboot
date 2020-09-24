package com.md.controller;

import com.md.entity.other.Meta;
import com.md.entity.goodsVo.ReportResult;
import com.md.entity.goodsVo.Result;
import com.md.service.goods.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/reports/type/1")
    public Result findAllReport() throws Exception{
        ReportResult report = reportService.findAllReport();
        Meta meta = Meta.succ("获取报表成功");
        Result result = Result.succ(meta, report);
        return result;
    }
}
