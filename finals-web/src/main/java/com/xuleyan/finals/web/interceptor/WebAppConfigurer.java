/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.interceptor;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.xuleyan.finals.web.converter.MaskJackson2HttpMessageConverter;
import com.xuleyan.finals.web.converter.MaskStringHttpMessageConverter;
import com.xuleyan.finals.web.httpconverter.MyMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author xuleyan
 * @version WebAppConfigurer.java, v 0.1 2021-07-19 8:12 下午
 */
@Slf4j
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Resource
    private MaskJackson2HttpMessageConverter maskJackson2HttpMessageConverter;

    @Resource
    private MaskStringHttpMessageConverter maskStringHttpMessageConverter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("注入 CheckUserInterceptor");
        registry.addInterceptor(new CheckUserInterceptor()).addPathPatterns("/**");
    }

    /**
     * 为空的数据直接省略，如list null => []，String null => "", boolean null => false
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        for (int i = 0; i < converters.size(); i++) {
            if (converters.get(i).getClass().equals(MappingJackson2HttpMessageConverter.class)) {
                converters.set(i, maskJackson2HttpMessageConverter);
            }
            if (converters.get(i).getClass().equals(StringHttpMessageConverter.class)) {
                converters.set(i, maskStringHttpMessageConverter);
            }
        }
        FastJsonHttpMessageConverter fjc = new FastJsonHttpMessageConverter();
        FastJsonConfig fj = new FastJsonConfig();
        fj.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
        fjc.setFastJsonConfig(fj);
        converters.add(fjc);
        //converters.add(converter());
    }

    @Bean
    public MyMessageConverter converter() {
        return new MyMessageConverter();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

    }


}