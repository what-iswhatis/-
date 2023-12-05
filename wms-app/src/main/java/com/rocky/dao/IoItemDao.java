package com.rocky.dao;

import com.gmw.annotation.PageX;
import com.rocky.model.IoItem;
import com.rocky.model.IoItemQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Mapper
public interface IoItemDao {

    @PageX
    List<IoItem> select(IoItemQuery query);
    int insert(List<IoItem> items);
}
