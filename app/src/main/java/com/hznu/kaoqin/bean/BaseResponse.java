package com.hznu.kaoqin.bean;

/**
 * Created by 代码咖啡 on 17/3/27
 * <p>
 * Email: wjnovember@icloud.com
 */

public class BaseResponse<T> {

    private int code;
    private String message;
    private T result;

    public BaseResponse() {

    }

    public BaseResponse(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
