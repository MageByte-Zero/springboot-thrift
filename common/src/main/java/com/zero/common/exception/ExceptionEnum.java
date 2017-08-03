package com.zero.common.exception;

import com.zero.common.ResultInfo;
import org.apache.commons.lang3.ArrayUtils;

public enum ExceptionEnum implements IException {

    SUCCESS(00000, "OK"),
    FAIL(99999, "fail"),

    RESULT_IS_NULL(10000, "resultVO is null");

    public int code;
    public String message;

    ExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMessage() {
        return null;
    }

    public final <T> ResultInfo<T> buildResultVO(ResultInfo<T> resultVO, T... arg) throws ServiceException {
        if (null == resultVO) {
            throw new ServiceException(RESULT_IS_NULL);
        }
        T result = ArrayUtils.isNotEmpty(arg) ? arg[0] : null;
        resultVO.setResultData(result);
        resultVO.setCode(code);
        resultVO.setMsg(message);
        return resultVO;
    }
}
