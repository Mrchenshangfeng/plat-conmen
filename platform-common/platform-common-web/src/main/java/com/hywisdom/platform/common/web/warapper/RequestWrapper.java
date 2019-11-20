package com.hywisdom.platform.common.web.warapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.hywisdom.platform.common.utils.AntiSQLFilter;
import com.hywisdom.platform.common.web.kit.RequestKit;
import org.springframework.web.util.HtmlUtils;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

/**
 * 〈功能简述〉<br>
 * 〈Request包装类
 * 1.预防xss攻击
 * 2.拓展requestbody无限获取(HttpServletRequestWrapper只能获取一次)
 * 3.防止sql注入〉
 *
 * @author wangxz
 * @create 2018/11/5
 * @since 1.0.0
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> safeParameterMap;
    /**
     * 存储requestBody byte[]
     */
    private final byte[] body;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        this.body = RequestKit.getByteBody(request);
    }

    @Override
    public BufferedReader getReader() {
        ServletInputStream inputStream = getInputStream();
        return Objects.isNull(inputStream) ? null : new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public ServletInputStream getInputStream() {
        if (ObjectUtils.isEmpty(body)) {
            return null;
        }
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() {
                return bais.read();
            }
        };
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = getParameterMap().get(name);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = htmlEscape(values[i]);
        }
        return encodedValues;
    }


    @Override
    public String getParameter(String name) {
        String[] values = getParameterValues(name);
        if (values != null && values.length > 0) {
            return htmlEscape(values[0]);
        } else {
            return null;
        }
    }

    @Override
    public Object getAttribute(String name) {
        Object value = super.getAttribute(name);
        if (value instanceof String) {
            htmlEscape((String) value);
        }
        return value;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }
        return htmlEscape(value);
    }

    @Override
    public String getQueryString() {
        String value = super.getQueryString();
        if (value == null) {
            return null;
        }
        return htmlEscape(value);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        if (safeParameterMap == null) {
            Map<String, String[]> originalParameterMap = super.getParameterMap();
            safeParameterMap = AntiSQLFilter.getSafeParameterMap(originalParameterMap);
        }
        return safeParameterMap;
    }

    /**
     * 使用spring HtmlUtils 转义html标签达到预防xss攻击效果
     *
     * @param str
     * @see org.springframework.web.util.HtmlUtils#htmlEscape
     */
    protected String htmlEscape(String str) {
        return HtmlUtils.htmlEscape(str);
    }
}
