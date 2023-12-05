package com.rocky.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Data
public class OoMaster extends BaseModel {
    private String id;
    private String poId;
    private LocalDate ooDate;
    private String brief;
    private String operator;
    private List<OoItem> items;
}
