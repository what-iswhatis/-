package com.rocky.enums;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Optional;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
public enum PoStatus {

    NEW(0, "新建(待审核)"),
    APPROVE(5, "审核通过"),
    DRIVING(10, "在途"),
    COMPLETE(15, "入库完成"),
    Remove(16, "出库成功"),
    REJECT(20, "审核未通过"),
    CANCEL(50, "作废");

    @Setter
    @Getter
    private Integer code;
    @Setter
    @Getter
    private String desc;

    PoStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PoStatus findByCode(Integer code) {
        Optional<PoStatus> first = Arrays.stream(PoStatus.values()).filter(item -> item.getCode().equals(code)).findFirst();
        return first.orElse(null);
    }

}
