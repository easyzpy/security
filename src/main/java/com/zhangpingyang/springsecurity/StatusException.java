package com.zhangpingyang.springsecurity;

public class StatusException extends RuntimeException {
    private String errorMsg;
    private Integer code;

    public StatusException() {
    }

    public StatusException(Integer code, String errorMsg) {
        this.errorMsg = errorMsg;
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
