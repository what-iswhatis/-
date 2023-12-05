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
public enum ProductStatus {

    SALE(2, "上架"),
    OFF_SALE(1, "下架"),
    DELETE(0, "删除");
    @Setter @Getter
    private Integer code;
    @Setter @Getter
    private String desc;

    ProductStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ProductStatus findByCode(Integer code) {
        Optional<ProductStatus> first = Arrays.stream(ProductStatus.values()).filter(item -> item.getCode().equals(code)).findFirst();
        return first.orElse(null);
    }

}
