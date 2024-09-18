package cn.hb.core;

/**
 * @Author: abin
 * @Date: 2024/9/17 19:59
 * @Description: 基本功能请求参数
 */

public class BaseFuncParam {
    private Integer resLength;
    private Integer resNum;
    private String funcType;

    public Integer getResLength() {
        return resLength;
    }

    public void setResLength(Integer resLength) {
        this.resLength = resLength;
    }

    public Integer getResNum() {
        return resNum;
    }

    public void setResNum(Integer resNum) {
        this.resNum = resNum;
    }

    public String getFuncType() {
        return funcType;
    }

    public void setFuncType(String funcType) {
        this.funcType = funcType;
    }
}
