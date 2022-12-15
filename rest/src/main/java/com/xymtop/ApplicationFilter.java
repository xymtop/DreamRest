package com.xymtop;



import com.xymtop.Ann.Router;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : ApplicationFilter
 * @Description : 主过滤器
 * @Author : 肖叶茂
 * @Date: 2022/12/15  22:57
 */
public class ApplicationFilter implements Filter {

    private Map<String,Class> RouteMap = new HashMap<>();
    private  Map<String,Method> MethodMap = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        new Router().getRouter(request,response);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
