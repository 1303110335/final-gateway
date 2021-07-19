/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.advice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xuleyan.finals.web.annotation.Masking;
import com.xuleyan.finals.web.httpconverter.MaskValueFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author xuleyan
 * @version MaskResponseBodyAdvise.java, v 0.1 2021-07-20 6:20 上午
 */
@ControllerAdvice
@Slf4j
public class MaskResponseBodyAdvise implements ResponseBodyAdvice<String> {

    @Value("${masking.field}")
    private String maskingField;

    private Map<String, String[]> fieldAndRules = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("init maskingField : {}", maskingField);
        // {"name":"0&1", "idcard":"1&1","phone":"3&2"}
        Map<String, String> maps = (Map<String, String>) JSONObject.parse(maskingField);
        if (maps != null) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                String[] split = value.split("&");
                if (split.length < 2) {
                    log.warn("规则配置错误 key = {}, value = {}", key, value);
                    continue;
                }
                fieldAndRules.put(key, split);
            }
        }
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.getMethod() != null && methodParameter.getMethod().isAnnotationPresent(Masking.class);
    }

    @Override
    public String beforeBodyWrite(String body, MethodParameter methodParameter, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Object bodyObject = JSON.parse(body);
        // [{"id":1,"name":"xly"}]
        String result = JSON.toJSONString(bodyObject, new MaskValueFilter(fieldAndRules), SerializerFeature.WriteMapNullValue);
        return result;
    }
}