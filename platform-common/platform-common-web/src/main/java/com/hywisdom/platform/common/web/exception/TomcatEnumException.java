package com.hywisdom.platform.common.web.exception;

/**
 * 〈功能简述〉<br>
 * 〈枚举异常〉
 *
 * @author wangxz
 * @create 2018/11/5
 * @since 1.0.0
 */
public class TomcatEnumException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TomcatEnumException(String message) {
        super(message);
    }

    public TomcatEnumException(Throwable throwable) {
        super(throwable);
    }

    public TomcatEnumException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
