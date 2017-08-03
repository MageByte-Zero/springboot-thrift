package com.zero.common;

import com.zero.common.exception.ExceptionEnum;

import java.io.Serializable;

/**
 * 通用DTO结果集
 * Created by jianqing.li on 2017/6/19.
 */
public class ResultInfo<T> implements Serializable {

    private boolean success = false;

    private int code;

    private String msg;

    private T resultData;

    public ResultInfo() {
    }

    public ResultInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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

    public boolean isSuccess() {
        if (code == ExceptionEnum.SUCCESS.getCode()) {
            this.success = true;
        }
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
