package com.rocky.dao;

import com.rocky.model.InventoryLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Mapper
public interface InventoryLogDao {

    int insert(InventoryLog model);

}
