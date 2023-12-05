package com.rocky.model;

import lombok.Data;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Data
public class InventoryLog extends BaseModel {
    private Integer id;
    private Integer productId;
    private Integer beforeQty;
    private Integer optionQty;
    private Integer afterQty;
    private Integer optionType;
    private String refId;
}

