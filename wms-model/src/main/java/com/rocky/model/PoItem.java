package com.rocky.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Data
public class PoItem extends  BaseModel {
    /**
     * 唯一编号
     */
    private Integer id;

    /**
     * 采购单id
     */
    private String poId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer qty;

    /**
     * 备注
     */
    private String brief;


}
