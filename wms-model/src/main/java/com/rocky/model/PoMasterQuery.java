package com.rocky.model;

import lombok.Data;

import java.util.Date;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Data
public class PoMasterQuery {

    private String id;

    private String[] ids;

    private Integer vendorId;

    private Date poDate;

    private Integer status;

    private String purchaser;

}