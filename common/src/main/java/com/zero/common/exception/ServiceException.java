package com.zero.common.exception;

public class ServiceException extends Exception {
    private ExceptionEnum exceptionEnum;

    public ServiceException() {
        this.exceptionEnum = exceptionEnum;
    }
    public ServiceException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

    public ServiceException(String message, ExceptionEnum exceptionEnum) {
        super(message);
        this.exceptionEnum = exceptionEnum;
    }

    public ServiceException(String message, Throwable cause, ExceptionEnum exceptionEnum) {
        super(message, cause);
        this.exceptionEnum = exceptionEnum;
    }

    public ServiceException(Throwable cause, ExceptionEnum exceptionEnum) {
        super(cause);
        this.exceptionEnum = exceptionEnum;
    }

    public ExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }

    public void setExceptionEnum(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }
}
