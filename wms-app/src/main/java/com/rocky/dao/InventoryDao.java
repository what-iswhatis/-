package com.rocky.dao;

import com.gmw.annotation.PageX;
import com.rocky.model.Inventory;
import com.rocky.model.InventoryQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */

@Mapper
public interface InventoryDao {
    @PageX
    List<Inventory> select(InventoryQuery query);

    int insert(Inventory model);

    int update(int productId,  int optionQty);


}