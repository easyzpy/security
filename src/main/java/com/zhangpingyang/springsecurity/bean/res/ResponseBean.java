package com.zhangpingyang.springsecurity.bean.res;

public class ResponseBean {
    int code;
    String msg;
    Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseBean error(String errMsg) {
        this.code = -1;
        this.msg = errMsg;
        return this;
    }
    public ResponseBean success() {
        this.code = 0;
        this.msg = "";
        return this;
    }
    public ResponseBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseBean() {
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
}
