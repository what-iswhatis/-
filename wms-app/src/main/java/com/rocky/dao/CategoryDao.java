package com.rocky.dao;


import com.gmw.annotation.PageX;
import com.rocky.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Mapper
public interface CategoryDao {
    @PageX
    List<Category> select(CategoryQuery query);

    int insert(Category category);

    int update(Category category);

    int delete(Integer id);
}
