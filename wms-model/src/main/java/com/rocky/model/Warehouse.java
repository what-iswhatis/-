package com.rocky.model;

import lombok.Data;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Data
public class Warehouse extends BaseModel {
    /**
     * 唯一编号
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String brief;

    /**
     * 父Id
     */
    private Integer parentId;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 状态
     */
    private Integer status = 1;

}