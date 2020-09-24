package com.md.entity.goodsVo;

import com.md.entity.other.Legend;
import com.md.entity.other.Series;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Data
public class ReportResult implements Serializable {

    private Legend legend;// 地区数据
    private List<HashMap<String,String>> yAxis;// y轴类型
    private List<String> xAxis;// x轴类型
    private List<Series> series;// 系列

}
