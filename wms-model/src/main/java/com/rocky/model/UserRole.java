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
public class UserRole   {
    private Integer id;
    private String name;
}