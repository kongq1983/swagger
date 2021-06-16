package com.kq.swagger.more.module.dto;

/**
 * @author kq
 * @date 2021-06-04 8:17
 * @since 2020-0630
 */
public class DtoResult {

    private String code;
    private Object result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "DtoResult{" +
                "code='" + code + '\'' +
                ", result=" + result +
                '}';
    }
}
