package com.zero.common;

import java.io.Serializable;

/**
 * 通用DTO结果集
 * Created by jianqing.li on 2017/6/19.
 */
public class ResultInfo<T> implements Serializable {

    private String code;

    private String msg;

    private T resultData;

    public ResultInfo() {
    }

    public ResultInfo(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }
}
