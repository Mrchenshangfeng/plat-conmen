package com.hywisdom.platform.common.web.warapper;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;


/**
 * 〈功能简述〉<br>
 * 〈过滤器〉
 *
 * @author wangxz
 * @create 2018/11/5
 * @since 1.0.0
 */
@Component
@WebFilter(filterName = "mesFilter", urlPatterns = "/*")
public class MesFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse res,
                         FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        chain.doFilter(new RequestWrapper(req), res);
    }

    @Override
    public void init(FilterConfig config) {
    }
}
