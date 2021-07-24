/**
 * xuleyan.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.service.api.param;

import lombok.Data;

/**
 *
 * @author xuleyan
 * @version GoodsParam.java, v 0.1 2021-07-21 8:51 下午
 */
@Data
public class GoodsParam {

    /**
     * 商品Id
     */
    Integer id;

    /**
     * 查询记录id
     */
    String requestId;
}