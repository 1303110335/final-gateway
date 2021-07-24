/**
 * xuleyan.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.converter;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *
 * @author xuleyan
 * @version MaskStringHttpMessageConverter.java, v 0.1 2021-07-19 10:09 下午
 */
@Component
public class MaskStringHttpMessageConverter extends StringHttpMessageConverter {
    @Override
    protected void writeInternal(String str, HttpOutputMessage outputMessage) throws IOException {
        //System.out.println("String:writeInternal :" + str);
        super.writeInternal(str, outputMessage);
    }
}