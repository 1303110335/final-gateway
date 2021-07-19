/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.converter;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 *
 * @author xuleyan
 * @version MaskJackson2HttpMessageConverter.java, v 0.1 2021-07-19 9:52 下午
 */
@Component
public class MaskJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        System.out.println("writeInternal :" + JSON.toJSONString(object));
        super.writeInternal(object, type, outputMessage);
    }
}