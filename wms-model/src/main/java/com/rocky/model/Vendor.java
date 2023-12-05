package com.rocky.model;

import lombok.Data;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Data
public class Vendor extends  BaseModel {
    /**
     * 唯一编号
     */
    private Integer id;

    /**
     * 供应商名称
     */
    private String name;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区、县
     */
    private String county;

    /**
     * 详细地址 门牌号
     */
    private String address;

    /**
     * 联系方式
     */
    private String tel;

    /**
     * 门面图、证书、许可证
     */
    private String img;

    /**
     * 介绍
     */
    private String brief;

    /**
     * 状态
     */
    private Integer status =1;

    /**
     * 排序
     */
    private Integer seq;



}