package com.rocky.model;

import com.rocky.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseModel {
    /**
     * 唯一编号
     */
    private Integer id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 副标题
     */
    private String subName;

    /**
     * 品类id
     */
    private Integer categoryId;

    /**
     * 图片
     */
    private String img;

    /**
     * 状态
     */
    private Integer status = 1;

    private String statusX;
    public String getStatusX() {
        return ProductStatus.findByCode(this.status).getDesc();
    }

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     *
     */
    private String brief;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 标签
     */
    private String tags;
}
