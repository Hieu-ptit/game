package com.mc.exception;

public class BusinessErrorCode {
    int code;
    String message;
    int httpStatus;

    public BusinessErrorCode() {
    }

    public BusinessErrorCode(int code) {
        this.code = code;
    }

    public BusinessErrorCode(int code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public BusinessErrorCode(String message, int httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }
}
