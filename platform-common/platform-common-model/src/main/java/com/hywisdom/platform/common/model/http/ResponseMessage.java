package com.hywisdom.platform.common.model.http;

/**
 * 〈功能简述〉<br>
 * 〈rest响应结果〉
 *
 * @author wangxz
 * @create 2018/10/18
 * @since 1.0.0
 */
public class ResponseMessage<T> {

    private String code;
    private String message;
    private T data;

    public ResponseMessage() {
    }

    public ResponseMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseMessage(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk() {
        return this.code.equals(ResponseMessageCodeEnum.SUCCESS.getCode());
    }
}
