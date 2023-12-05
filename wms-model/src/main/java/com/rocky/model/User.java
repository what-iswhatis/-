package com.rocky.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Data
public class User {
    private Integer id;
    private String tel;
    private String password;
    private String nickName;
    private LocalDateTime createTime;
}
