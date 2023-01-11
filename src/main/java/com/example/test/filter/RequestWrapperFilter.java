package com.example.test.filter;

import com.example.test.config.BodyReaderHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@Slf4j
@Component
@WebFilter(filterName = "RequestWrapperFilter", urlPatterns = "/**")
public class RequestWrapperFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException
            , IOException {
        // 排除multipart/form-data类型的请求，不以json形式读取
        String requestContentType=request.getContentType();
        if (requestContentType != null && requestContentType.contains("multipart/form-data")) {
            chain.doFilter(request, response);
            return;
        }

        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
        }
        if (null == requestWrapper) {
            log.error("过滤器包装request失败!将返回原来的request");
            chain.doFilter(request, response);
        } else {
            log.info("过滤器包装request成功");
            chain.doFilter(requestWrapper, response);
        }
    }
    @Override
    public void destroy() {
    }
}

