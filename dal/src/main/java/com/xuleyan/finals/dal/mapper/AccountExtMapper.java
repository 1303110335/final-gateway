/**
 * xuleyan.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.dal.mapper;

import org.apache.ibatis.annotations.Param;

/**
 *
 * @author xuleyan
 * @version AccountExtMapper.java, v 0.1 2021-07-17 9:56 上午
 */
public interface AccountExtMapper extends AccountMapper {

    public Integer updateById(@Param("id") Integer id);
}