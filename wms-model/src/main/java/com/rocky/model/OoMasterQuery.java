package com.rocky.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Data
public class OoMasterQuery {
    private String id;
    private String[] ids;
    private String poId;
    private LocalDate ooDate;
}
