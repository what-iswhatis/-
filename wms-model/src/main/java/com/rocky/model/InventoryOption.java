package com.rocky.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Data
public class InventoryOption  {

    private Integer productId;
    private String productName;
    private Integer shelfId;
    private Integer qty;
    private Integer optionType;  // +1  -1
    private LocalDate productionDate;
    private Integer expirationDay;
    private String refId;

}
