package com.rocky.model;

import lombok.Data;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Data
public class VendorQuery extends  BaseModel  {
    private Integer id;

    private Integer[] ids;

    private String name;

    /**
     * 省份
     */
    private String province;

    private Integer status = 1;

}