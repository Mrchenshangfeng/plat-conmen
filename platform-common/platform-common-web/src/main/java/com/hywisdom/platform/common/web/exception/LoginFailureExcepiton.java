package com.hywisdom.platform.common.web.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @Class_name: LoginFailureExcepiton
 * @Package: com.interest.controller.login
 * @Version: v1.0
 * @Description: 登录失败异常信息
 * @Author: xfl
 * @Date: 2018/7/11
 **/
@Slf4j
public class LoginFailureExcepiton extends RuntimeException {

    private static final long serialVersionUID = 1381277361046202535L;

    public LoginFailureExcepiton(String message) {
        super(message);
        log.error("LoginFailureExcepiton:" + message);
    }
}
