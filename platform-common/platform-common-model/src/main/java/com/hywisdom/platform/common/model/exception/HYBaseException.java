package com.hywisdom.platform.common.model.exception;

/**
 * 〈功能简述〉<br>
 * 〈异常基类〉
 *
 * @author wangxz
 * @create 2018/10/18
 * @since 1.0.0
 */
public class HYBaseException extends RuntimeException {

    private String errorCode;

    public HYBaseException() {
    }

    public HYBaseException(String message) {
        this("-1", message);
    }

    public HYBaseException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
