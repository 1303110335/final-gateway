/**
 * xuleyan.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.filter;

//import com.xuleyan.frame.tracer.filter.HttpTracerFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author xuleyan
 * @version FilterConfig.java, v 0.1 2021-07-19 8:37 下午
 */
@Configuration
@Slf4j
public class FilterConfig {

//    @Bean
//    public FilterRegistrationBean registrationBean() {
//        log.info("注入 registrationBean");
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new MyFilter());
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
//        return registrationBean;
//    }
//
//    @Bean
//    public FilterRegistrationBean registrationBean2() {
//        log.info("注入 registrationBean");
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new MyFilter2());
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }

//    @Bean
//    public FilterRegistrationBean registerFilter() {
//        log.info("注入 registrationBean >> httpFilter");
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new HttpTracerFilter());
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }
}