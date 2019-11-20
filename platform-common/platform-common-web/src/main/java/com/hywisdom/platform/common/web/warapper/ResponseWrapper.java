package com.hywisdom.platform.common.web.warapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 〈功能简述〉<br>
 * 〈response包装类〉
 *
 * @author wangxz
 * @create 2018/11/5
 * @since 1.0.0
 */
@Slf4j
public class ResponseWrapper extends HttpServletResponseWrapper {

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }
    private HttpServletResponse _getHttpServletResponse() {
        return (HttpServletResponse) super.getResponse();
    }
}
