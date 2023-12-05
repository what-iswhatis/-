package com.rocky.model;

import lombok.Data;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Data
public class WarehouseQuery {

    private Integer id;
    private Integer[] ids;
    private String name;
    private Integer parentId;
    private Integer status = 1;

}