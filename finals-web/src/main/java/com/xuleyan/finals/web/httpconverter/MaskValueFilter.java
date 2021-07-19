/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.httpconverter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.xuleyan.finals.web.domain.User;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author xuleyan
 * @version MaskValueFilter.java, v 0.1 2021-07-19 10:35 下午
 */
public class MaskValueFilter implements ValueFilter {


    private final Map<String, String[]> rules;

    public MaskValueFilter(Map<String, String[]> rules) {
        this.rules = rules;
    }

    public static void main(String[] args) {
        User obj = new User();
        obj.setName("徐乐雁");
        obj.setAge(null);
        obj.setPhone("18367829627");

        List<User> userList = new ArrayList<>();
        userList.add(obj);
        Map<String, String[]> rules = new HashMap<>();
        rules.put("name", new String[] {"0", "1"});
        rules.put("phone", new String[] {"3", "2"});
        System.out.println(JSON.toJSONString(userList, new MaskValueFilter(rules)));
    }

    @Override
    public Object process(Object object, String name, Object value) {
        if (value instanceof String && rules.containsKey(name)) {
            String[] values = rules.get(name);
            int left = Integer.parseInt(values[0]);
            int right = Integer.parseInt(values[1]);
            String valueStr = (String) value;

            // 保留左left,右right, 其他*
            int length = valueStr.length();
            return valueStr.substring(0, left) +
                    StringUtils.repeat("*", length - left - right) +
                    valueStr.substring(length - right);
        }
        return value;
    }
}