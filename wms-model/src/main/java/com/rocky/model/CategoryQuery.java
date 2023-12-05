package com.rocky.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryQuery {
    private Integer id; //支持单个id查询
    private Integer[] ids; //支持多个id查询
    private String name;
    private Integer parentId;
    private Integer status = 1;
    private String lastUpdateBy;
}
