package com.zxg.mybanner.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/21.
 * base基类，定义bean类共同值
 */
//{
//        success: false,
//        msg: 登录超时,
//        code: e001,
//        "pageNo": 1,
//        "pageSize": 1,
//        "totalPages": 1,
//        "totalCount": 1
//        }
public class BaseBean implements Serializable {

    /**
     * success : false
     * msg : 登录超时
     * code : e001
     * pageNo : 1
     * pageSize : 1
     * totalPages : 1
     * totalCount : 1
     */

    private boolean success;
    private String msg;
    private String code;
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private int totalCount;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "code='" + code + '\'' +
                ", success=" + success +
                ", msg='" + msg + '\'' +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", totalPages=" + totalPages +
                ", totalCount=" + totalCount +
                '}';
    }
}
