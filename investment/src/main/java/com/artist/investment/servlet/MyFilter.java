package com.artist.investment.servlet;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 *
 * Created by asiam on 2017/4/20 0020.
 */
//@Component("myFilter") //Component方式的myFilter配置的映射是/*
@WebFilter(filterName="myFilter",urlPatterns="/*")
public class MyFilter implements Filter {
    /**
     *
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        System.out.println("destroy...");
    }
    /**
     * @param arg0
     * @param arg1
     * @param arg2
     * @throws IOException
     * @throws ServletException
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
            throws IOException,
            ServletException {
        System.out.println("doFilter...");
        arg2.doFilter(arg0, arg1);
    }
    /**
     * @param arg0
     * @throws ServletException
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        System.out.println("filter init...");
    }
}
