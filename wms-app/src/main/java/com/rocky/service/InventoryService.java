package com.rocky.service;

import cn.hutool.core.util.ObjectUtil;
import com.rocky.dao.InventoryDao;
import com.rocky.dao.InventoryLogDao;
import com.rocky.model.Inventory;
import com.rocky.model.InventoryLog;
import com.rocky.model.InventoryOption;
import com.rocky.model.InventoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Service
public class InventoryService {


    @Autowired
    InventoryDao inventoryDao;
    @Autowired
    InventoryLogDao logDao;


    public List<Inventory> select(InventoryQuery query) {
        return inventoryDao.select(query);
    }


    public Integer optionInventory(InventoryOption option) {
        InventoryLog log = new InventoryLog();
        // 1. 之前数据  之后数据的初始化
        int beforeQty = 0;
        int afterQty = 0;
        int optionQty = option.getQty();
        // 2. 查询有没有在库存表
        List<Inventory> inventoryList = inventoryDao.select(InventoryQuery.builder().productId(option.getProductId()).build());

        if(ObjectUtil.isNotEmpty(inventoryList)){
            // 3. 在库存表
            beforeQty = inventoryList.get(0).getQty(); //当前库存表里面的数量
            afterQty = beforeQty + optionQty * option.getOptionType();
            //更新库存表
            inventoryDao.update(option.getProductId(),optionQty * option.getOptionType());
        } else {
            // 3. 不在库存表
            Inventory inventory = Inventory.builder()
                    .productId(option.getProductId())
                    .productName(option.getProductName())
                    .productionDate(option.getProductionDate())
                    .expirationDay(option.getExpirationDay())
                    .expirationDate(option.getProductionDate().plusDays(option.getExpirationDay()))
                    .qty(optionQty * option.getOptionType())
                    .shelfId(option.getShelfId())
                    .build();
            inventoryDao.insert(inventory);
            afterQty = beforeQty + optionQty * option.getOptionType();
        }
        log.setProductId(option.getProductId());
        log.setBeforeQty(beforeQty);
        log.setOptionQty(optionQty);
        log.setAfterQty(afterQty);
        log.setRefId(option.getRefId());
        log.setOptionType(option.getOptionType());
        logDao.insert(log);
        return 1;


    }
}
