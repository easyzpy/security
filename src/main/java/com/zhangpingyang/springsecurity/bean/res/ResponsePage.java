package com.zhangpingyang.springsecurity.bean.res;

/**
 * @Author: Zhang Pingyang
 * @Date: 2019/9/16 16:31
 */
public class ResponsePage extends ResponseBean{
    private int page;
    private long totalPage;
    private long totalRecord;

    public ResponsePage(int code, String msg) {
        super(code, msg);
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public ResponsePage() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
