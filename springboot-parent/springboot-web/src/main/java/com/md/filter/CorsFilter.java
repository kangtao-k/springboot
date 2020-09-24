package com.md.filter;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CorsFilter")
@Configuration
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //解决跨域访问报错
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Methods","POST,GET,DELETE,PUT,PATCH");
        response.setHeader("Access-Control-Max-Age","3600");
        response.setHeader("Access-Control-Request-Headers","*");
        response.setHeader("Access-Control-Allow-Headers","*");
//        response.setHeader("Allow","POST");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
