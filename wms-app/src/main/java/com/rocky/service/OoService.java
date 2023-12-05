package com.rocky.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.gmw.threadlocal.LocalUserUtil;
import com.rocky.dao.OoItemDao;
import com.rocky.dao.OoMasterDao;
import com.rocky.model.*;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OoService {

    @Autowired
    private OoMasterDao masterDao;

    @Autowired
    private OoItemDao ootemDao;

    @Autowired
    private InventoryService inventoryService;


    //新增出库单
    public Integer insert(OoMaster master) {
        String ooId = "OO" + DateUtil.format(DateUtil.date(), "yyyyMMddHHmmssSSS");
        master.setId(ooId);
        master.setOoDate(LocalDateTimeUtil.now().toLocalDate());
        List<OoItem> items = master.getItems();
        items.forEach(item -> {
            item.setOoId(ooId);
            item.setLastUpdateBy(LocalUserUtil.get().getNickName());
        });

        ((OoService) AopContext.currentProxy()).add(master);

        return 1;
    }


    @Transactional
    public void add(OoMaster master) {
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
            //出库单明细的过期日期计算
            item.setExpirationDate(item.getProductionDate().plusDays(item.getExpirationDay()));
            //
            inventoryService.optionInventory(option);
        });

        ootemDao.insert(master.getItems());
    }



    public List<OoMaster> select(OoMasterQuery query) {
        return masterDao.select(query);
    }

    public List<OoItem> selectItems(OoItemQuery query) {
        return ootemDao.select(query);
    }
}