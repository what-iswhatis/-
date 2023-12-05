package com.rocky.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.gmw.threadlocal.LocalUserUtil;
import com.rocky.dao.IoItemDao;
import com.rocky.dao.IoMasterDao;
import com.rocky.model.*;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Service
public class IoService {

    @Autowired
    private IoMasterDao masterDao;
    @Autowired
    private IoItemDao itemDao;
    @Autowired
    private InventoryService inventoryService;

    //新增入库单
    public Integer insert(IoMaster master) {
        String ioId = "IO" + DateUtil.format(DateUtil.date(), "yyyyMMddHHmmssSSS");
        master.setId(ioId);
        master.setIoDate(LocalDateTimeUtil.now().toLocalDate());
        List<IoItem> items = master.getItems();
        items.forEach(item -> {
            item.setIoId(ioId);
            item.setLastUpdateBy(LocalUserUtil.get().getNickName());
        });

        ((IoService) AopContext.currentProxy()).add(master);

        return 1;
    }

    @Transactional
    public void add(IoMaster master) {
        masterDao.insert(master);

        master.getItems().forEach(item -> {
            InventoryOption option = new InventoryOption();
            option.setProductId(item.getProductId());
            option.setProductName(item.getProductName());
            option.setQty(item.getQty());
            option.setOptionType(1);
            option.setExpirationDay(item.getExpirationDay());
            option.setProductionDate(item.getProductionDate());
            option.setRefId(master.getId());
            option.setShelfId(item.getShelfId());
            //入库单明细的过期日期计算
            item.setExpirationDate(item.getProductionDate().plusDays(item.getExpirationDay()));
            //
            inventoryService.optionInventory(option);
        });

        itemDao.insert(master.getItems());
    }


    public List<IoMaster> select(IoMasterQuery query) {
        return masterDao.select(query);
    }

    public List<IoItem> selectItems(IoItemQuery query) {
        return itemDao.select(query);

    }
}
