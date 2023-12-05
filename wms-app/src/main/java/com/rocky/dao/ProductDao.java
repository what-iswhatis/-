package com.rocky.dao;


import com.gmw.annotation.PageX;
import com.rocky.model.Product;
import com.rocky.model.ProductQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Mapper
public interface ProductDao {
    @PageX
    List<Product> select(ProductQuery query);

    int insert(Product product);

    int update(Product product);

    int delete(Integer id);
}
