package com.kq.swagger.customize.config.dto;

/**
 * @author kq
 * @date 2021-06-04 8:17
 * @since 2020-0630
 */
public class DtoGenericResult<T> {

    private String code;
    private T result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "DtoGenericResult{" +
                "code='" + code + '\'' +
                ", result=" + result +
                '}';
    }
}
