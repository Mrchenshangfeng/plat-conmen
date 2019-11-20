package com.hywisdom.platform.common.model.http;

/**
 * 〈功能简述〉<br>
 * 〈rest验证参数异常〉
 *
 * @author wangxz
 * @create 2018/10/18
 * @since 1.0.0
 */
public class ValidError {

    private String field;
    private String message;

    public ValidError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
