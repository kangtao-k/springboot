package com.md.entity.other;

import lombok.Data;

import java.io.Serializable;

@Data
public class Meta implements Serializable {
    private String msg;
    private Integer status;// 状态200是正常,非200表示异常

    public static Meta succ(String msg){
        return succ(200,msg);
    }

    public static Meta succ(Integer status,String msg){
        Meta m = new Meta();
        m.setStatus(status);
        m.setMsg(msg);
        return m;
    }

    public static Meta failed(String msg){
        return succ(500,msg);
    }
}
