package com.hywisdom.platform.common.web;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.hywisdom.platform.common.model.http.ResponseMessage;
import com.hywisdom.platform.common.model.http.Result;
import com.hywisdom.platform.common.utils.StringHelper;
import com.hywisdom.platform.common.web.exception.LoginFailureExcepiton;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.net.BindException;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 〈功能简述〉<br>
 * 〈controller基类〉
 *
 * @author wangxz
 * @create 2018/10/18
 * @since 1.0.0
 */
@RestControllerAdvice
@Slf4j
public class BaseController {

    /**
     * @param
     * @return
     * @description 参数验证
     * @author wangxz
     * @create 2018/10/18
     * @since 1.0.0
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseMessage MethodArgumentNotValidHandler(MethodArgumentNotValidException exception) throws Exception {
        log.error(exception.getMessage(), exception);
        return Result.error(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "参数验证失败");
    }

    /**
     * @param
     * @return
     * @description 参数绑定验证
     * @author wangxz
     * @create 2018/10/18
     * @since 1.0.0
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseMessage MethodArgumentNotValidHandler(BindException exception) throws Exception {
        log.error(exception.getMessage(), exception);
        return Result.error(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "缺少请求参数");
    }

    /**
     * @param
     * @return
     * @description 缺少参数
     * @author wangxz
     * @create 2018/10/18
     * @since 1.0.0
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST )
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseMessage handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        log.error(exception.getMessage(), exception);
        return Result.error(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "缺少请求参数");
    }

    /**
     * @param
     * @return
     * @description 参数解析
     * @author wangxz
     * @create 2018/10/18
     * @since 1.0.0
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseMessage handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error(exception.getMessage(), exception);
        return Result.error(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "缺少请求参数");
    }


    /**
     * @param
     * @return
     * @description 参数验证失败
     * @author wangxz
     * @create 2018/10/18
     * @since 1.0.0
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseMessage handleServiceException(ConstraintViolationException exception) {
        log.error(exception.getMessage(), exception);
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();

        return Result.error(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                message);
    }

    /**
     * @param
     * @return
     * @description 参数验证
     * @author wangxz
     * @create 2018/10/18
     * @since 1.0.0
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResponseMessage handleValidationException(ValidationException exception) {
        log.error(exception.getMessage(), exception);
        return Result.error(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "参数验证失败");
    }

    /**
     * @param
     * @return
     * @description 不支持当前请求
     * @author wangxz
     * @create 2018/10/18
     * @since 1.0.0
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseMessage handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        log.error(exception.getMessage(), exception);
        return Result.error(String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()),
                "不支持当前请求");
    }

    /**
     * @param
     * @return
     * @description 不支持当前媒体类型
     * @author wangxz
     * @create 2018/10/18
     * @since 1.0.0
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseMessage handleHttpMediaTypeNotSupportedException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return Result.error(String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()),
                "不支持当前媒体类型");
    }

    /**
     * @param
     * @return
     * @description 系统异常
     * @author wangxz
     * @create 2018/10/18
     * @since 1.0.0
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseMessage handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return Result.error(String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()),
                "系统异常，请稍后重试");
    }

    /**
     * 功能描述: <br>
     * 〈登录失败〉
     *
     * @return:
     * @since: 1.0.0
     * @Author:Thinkpad
     * @Date: 2018/10/31 11:54
     */
    @ExceptionHandler(LoginFailureExcepiton.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseMessage handleLoginFailureExcepiton(LoginFailureExcepiton exception) {
        log.error(exception.getMessage(), exception);
        return Result.error(String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                "登陆失败，请稍后重试");
    }

    /**
     * 功能描述: <br>
     * 〈附件上传大小限制异常处理〉
     *
     * @param
     * @since: 1.0.0
     * @Author:wangxz
     * @Date: 2019/3/9 17:33
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage handleMaxUploadSizeException(MaxUploadSizeExceededException exception) {
        log.error(exception.getMessage(), exception);
        return Result.error("上传文件不能超过50MB,上传文件失败");
    }

//    @ResponseBody
//    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
////    @ExceptionHandler(AccessDataException.class)
//    public ResponseMessage handleAccessDataException(Exception exception) {
//
//        logger.error("数据操作权限异常", exception);
//        return Result.error(String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()),
//                "数据操作权限异常");
//    }

    /**
     * 获取IP地址
     * @param request
     * @return
     */
    protected String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("X-Real-IP");
        }

        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getRemoteAddr();
            if (("127.0.0.1".equals(ip)) || ("0:0:0:0:0:0:0:1".equals(ip))) {
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        return ip;
    }

    /**
     * 获取QueryString的参数
     *
     * @param request web请求
     * @param prefix 前缀字符
     * @return Map<String, String> 参数键值集合
     */
    protected Map<String, String> getRequestMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<String> names = request.getParameterNames();
        String name;
        while (names.hasMoreElements()) {
            name = names.nextElement();
            map.put(name.trim(), request.getParameter(name));
        }
        return map;
    }

    /**
     * 判断是否为手机浏览器请求
     *
     * @return
     */
    protected boolean isMobileRequest(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        return userAgent.contains("Mobile") && !userAgent.contains("iPad");
    }
}
