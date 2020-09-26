package com.md.service.goods.impl;

import com.md.dao.ReportDao;
import com.md.entity.other.AreaStyle;
import com.md.entity.other.Legend;
import com.md.entity.other.Series;
import com.md.entity.goodsVo.ReportResult;
import com.md.entity.other.XAxis;
import com.md.service.goods.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Override
    public ReportResult findAllReport() throws Exception {
        ReportResult reportResult = new ReportResult();
//        查询出所有的地区集合
        List<String> dataName = reportDao.findLegend();
        Legend legend = new Legend();
        legend.setData(dataName);
        reportResult.setLegend(legend);
//        y轴数据
        HashMap<String, String> map = new HashMap<>();
        map.put("type","value");
        List<HashMap<String, String>> listY = new ArrayList<>();
        listY.add(map);
        reportResult.setyAxis(listY);
//        x轴数据
        List<String> listX = reportDao.findAllDate();
        XAxis xAxis = new XAxis();
        xAxis.setData(listX);
        ArrayList<XAxis> list = new ArrayList<>();
        list.add(xAxis);
        reportResult.setxAxis(list);
//        series数据
        List<Series> seriesList = new ArrayList<>();
        AreaStyle areaStyle = new AreaStyle();
        for (String name : dataName) {
            Series series = new Series();
            List<Integer> dataUsers = reportDao.findReportByName(name);// 找到用户数
            System.out.println(dataUsers);
            series.setAreaStyle(areaStyle);
            series.setName(name);
            series.setData(dataUsers);
            series.setType("line");
            series.setStack("总量");
            seriesList.add(series);
        }
        reportResult.setSeries(seriesList);
        return reportResult;
    }
}
