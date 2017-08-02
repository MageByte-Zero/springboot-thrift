package com.zero.common.exception;

public enum ExceptionEnum implements IException {

    SUCCESS(00000, "OK"),
    FAIL(99999, "fail");

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
}
