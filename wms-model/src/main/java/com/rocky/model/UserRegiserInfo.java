package com.rocky.model;

import lombok.Data;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Data
public class UserRegiserInfo {
    private String tel;
    private String password;
    private String nickName;
    private String email;
    private String vcode;
}
