package com.rocky.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inventory extends BaseModel {
    private Integer id;
    private Integer productId;
    private String productName;
    private Integer shelfId;
    private Integer qty;
    private Integer salesCount;
    private LocalDate productionDate;
    private Integer expirationDay;
    private LocalDate expirationDate;
}