package com.md.entity.goodsVo;

import com.alibaba.fastjson.annotation.JSONField;
import com.md.entity.other.Legend;
import com.md.entity.other.Series;
import com.md.entity.other.XAxis;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class ReportResult implements Serializable {

    @JSONField(ordinal = 1)
    private Legend legend;// 地区数据
    @JSONField(ordinal = 2,name = "yAxis")
    private List<HashMap<String,String>> yAxis;// y轴类型
    @JSONField(ordinal = 3,name = "xAxis")
    private List<XAxis> xAxis;// x轴类型
    @JSONField(ordinal = 4)
    private List<Series> series;// 系列

    public Legend getLegend() {
        return legend;
    }

    public void setLegend(Legend legend) {
        this.legend = legend;
    }

    public List<HashMap<String, String>> getyAxis() {
        return yAxis;
    }

    public void setyAxis(List<HashMap<String, String>> yAxis) {
        this.yAxis = yAxis;
    }

    public List<XAxis> getxAxis() {
        return xAxis;
    }

    public void setxAxis(List<XAxis> xAxis) {
        this.xAxis = xAxis;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }
}
