package com.hywisdom.platform.common.web.Interceptor;

import com.hywisdom.platform.common.model.exception.HYBaseException;
import com.hywisdom.platform.common.web.cons.Cons;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 〈功能简述〉<br>
 * 〈全局拦截器〉
 *
 * @author wangxz
 * @create 2018/11/5
 * @since 1.0.0
 */
public class GlobalInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // ResourceHttpRequestHandler放行
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 获取约定的请求头
        request.setAttribute(Cons.API_BEGIN_TIME, System.currentTimeMillis());
        // 获取请求路径
        setAttributeOfPath(request, handlerMethod);
        return super.preHandle(request, response, handler);
    }

    /**
     * 获取当前请求路径放入request中
     *
     * @param request
     */
    public void setAttributeOfPath(HttpServletRequest request, HandlerMethod handlerMethod) {
        RequestMapping requestMapping = handlerMethod.getBeanType().getAnnotation(RequestMapping.class);
        RequestMapping methodAnnotation = handlerMethod.getMethodAnnotation(RequestMapping.class);
        String mapping = "";
        if (requestMapping != null) {
            mapping = requestMapping.value()[0];
        }
        String methodMapping = "";
        if (methodAnnotation == null) {
            throw new HYBaseException("请求路径解析错误");
        }
        String[] methodMappings = methodAnnotation.value();
        if (ArrayUtils.isNotEmpty(methodMappings)) {
            methodMapping = methodMappings[0];
        }
        String requestMethod = methodAnnotation.method()[0].name();
        String requsetMapping = mapping + methodMapping;
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String reqUrl = requestUri.substring(contextPath.length());
        request.setAttribute(Cons.API_REQURL, reqUrl);
        request.setAttribute(Cons.API_MAPPING, requsetMapping);
        request.setAttribute(Cons.API_METHOD, requestMethod);
    }

}
