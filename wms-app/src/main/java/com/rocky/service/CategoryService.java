package com.rocky.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.jwt.JWTUtil;
import com.github.pagehelper.PageHelper;
import com.rocky.dao.CategoryDao;
import com.rocky.dao.UserDao;
import com.rocky.exception.Bizexception;
import com.rocky.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;


    public List<Category> select(CategoryQuery query) {
        return categoryDao.select(query);
    }

    public Integer insert(Category category) {

        return categoryDao.insert(category);
    }

    public Integer update(Category category) {
        return categoryDao.update(category);
    }

    public Integer delete(Integer id) {
        //软删除
        return categoryDao.delete(id);
    }
}

