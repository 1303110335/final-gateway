/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.holder;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author xuleyan
 * @version UserHolder.java, v 0.1 2021-07-31 8:10 上午
 */
public class UserHolder {

    private static ThreadLocal<AtomicInteger> userHolder = new InheritableThreadLocal<AtomicInteger>() {
        @Override
        protected AtomicInteger initialValue() {
            return new AtomicInteger(0);
        }
    };

    public static void increment() {
        userHolder.get().getAndIncrement();
    }

    public static AtomicInteger getUser() {
        return userHolder.get();
    }

    public static void remove() {
        userHolder.remove();
    }
}