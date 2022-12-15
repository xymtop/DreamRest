package com.xymtop.Interceptor;

/**
 * @ClassName : CharactorFilter
 * @Description : 字符串编码
 * @Author : 肖叶茂
 * @Date: 2022/12/13  15:12
 */


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * 字符编码过滤器
 *
 * @author pan_junbiao
 */

//使用注解来标记过滤器
@WebFilter(filterName = "CharactorFilter", urlPatterns = "/*",
        initParams = { @WebInitParam(name = "encoding", value = "UTF-8") })
public class CharactorFilter implements Filter
{
    // 字符编码
    String encoding = null;

    // 初始化方法
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        // 获取初始化参数
        encoding = filterConfig.getInitParameter("encoding");
    }

    // 过滤处理方法
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        // 判断字符编码是否为空
        if (encoding != null)
        {
            // 设置request的编码格式
            request.setCharacterEncoding(encoding);
            // 设置response字符编码
            response.setContentType("text/html; charset=" + encoding);
        }
        // 传递给下一过滤器
        chain.doFilter(request, response);
    }

    // 销毁方法
    @Override
    public void destroy()
    {
        encoding = null;
    }
}
