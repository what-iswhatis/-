package com.rocky.service;

import com.rocky.dao.WarehouseDao;
import com.rocky.model.Warehouse;
import com.rocky.model.WarehouseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseDao warehouseDao;


    public List<Warehouse> select(WarehouseQuery query) {
        return warehouseDao.select(query);
    }

    public Integer insert(Warehouse warehouse) {
        return warehouseDao.insert(warehouse);
    }

    public Integer update(Warehouse warehouse) {
        return warehouseDao.update(warehouse);
    }

    public Integer delete(Integer id) {
        return warehouseDao.delete(id);
    }
}