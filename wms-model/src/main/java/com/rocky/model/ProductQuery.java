package com.rocky.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuery {
    private Integer id;
    private Integer[] ids;
    private String name;
    private String subName;
    private Integer categoryId;
    private Integer status;
}
