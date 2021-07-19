/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author xuleyan
 * @version EncryptionHttpInputMessage.java, v 0.1 2021-07-19 6:02 下午
 */
public class EncryptionHttpInputMessage implements HttpInputMessage {


    private InputStream body;

    private HttpHeaders headers;

    public EncryptionHttpInputMessage() {
    }

    public EncryptionHttpInputMessage(InputStream body, HttpHeaders headers) {
        this.body = body;
        this.headers = headers;
    }

    @Override
    public InputStream getBody() throws IOException {
        return body;
    }

    public void setBody(InputStream body) {
        this.body = body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

}
