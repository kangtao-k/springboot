package com.md.entity.goodsVo;

import com.md.entity.other.Meta;
import lombok.Data;

import java.io.Serializable;

/**
 * 状态
 */
@Data
public class Result implements Serializable {

    private Object data;
    private Meta meta;

    public static Result succ(String msg, Object data){
        Result r = new Result();
        Meta meta = Meta.succ(msg);
        r.setMeta(meta);
        r.setData(data);
        return r;
    }

    public static Result succ(Meta meta, Object data){
        Result r = new Result();
        r.setMeta(meta);
        r.setData(data);
        return r;
    }
    public static Result failed(String msg){
        Meta meta = Meta.failed(msg);
        return succ(meta,null);
    }
}
