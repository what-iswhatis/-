package com.rocky.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Data
public class Bizexception extends RuntimeException{

    @Getter@Setter
    private int code;

    public Bizexception(int code, String msg){
        super(msg);
    }
}
