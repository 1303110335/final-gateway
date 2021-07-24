///**
// * bianque.com
// * Copyright (C) 2013-2021 All Rights Reserved.
// */
//package com.xuleyan.finals.web.filter;
//
//import javax.servlet.FilterConfig;
//import javax.servlet.*;
//import java.io.IOException;
//
///**
// *
// * @author xuleyan
// * @version MyFilter.java, v 0.1 2021-07-19 8:36 下午
// */
//public class MyFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        // do something 处理request 或response
//        //System.out.println("MyFilter");
//        // 调用filter链中的下一个filter
//        chain.doFilter(request,response);
//        //System.out.println("MyFilter end ...");
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void destroy() {
//        //System.out.println("MyFilter:destroy");
//    }
//}