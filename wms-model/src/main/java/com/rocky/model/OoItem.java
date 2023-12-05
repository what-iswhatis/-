package com.rocky.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Data
public class OoItem extends BaseModel {
    private Integer id;
    private String ooId;
    private Integer productId;
    private String productName;
    private Integer shelfId;
    private Integer qty;
    private String brief;
    private Integer poItemId;
    private LocalDate productionDate;
    private Integer expirationDay;
    private LocalDate expirationDate;
}
