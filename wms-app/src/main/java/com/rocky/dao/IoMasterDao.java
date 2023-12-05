package com.rocky.dao;

import com.gmw.annotation.PageX;
import com.rocky.model.IoMaster;
import com.rocky.model.IoMasterQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Mapper
public interface IoMasterDao {

    @PageX
    List<IoMaster> select(IoMasterQuery query);

    int insert(IoMaster model);

}