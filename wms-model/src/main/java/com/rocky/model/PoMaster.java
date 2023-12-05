package com.rocky.model;

import com.rocky.enums.PoStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Data
public class PoMaster extends  BaseModel {
    /**
     * 唯一标识
     */
    private String id;

    /**
     * 供应商id
     */
    private Integer vendorId;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 采购日期
     */
    private LocalDate poDate;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 备注
     */
    private String brief;

    /**
     * 状态
     */
    private Integer status;

    private String statusX;
    public String getStatusX() {
        return PoStatus.findByCode(this.status).getDesc();
    }

    /**
     * 采购人
     */
    private String purchaser;
    /**
     * 采购商品明细
     */
    public List<PoItem> items;

}
