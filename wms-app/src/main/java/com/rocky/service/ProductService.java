package com.rocky.service;

import com.rocky.dao.CategoryDao;
import com.rocky.dao.ProductDao;
import com.rocky.model.Category;
import com.rocky.model.CategoryQuery;
import com.rocky.model.Product;
import com.rocky.model.ProductQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;



    public List<Product> select(ProductQuery query) {
        return productDao.select(query);
    }

    public Integer insert(Product product) {

        return productDao.insert(product);
    }

    public Integer update(Product product) {
        return productDao.update(product);
    }

    public Integer delete(Integer id) {
        //软删除
        return productDao.delete(id);
    }
}

