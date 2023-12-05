package com.rocky.controller;

import com.rocky.model.Warehouse;
import com.rocky.model.WarehouseQuery;
import com.rocky.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {


    private WarehouseService warehouseService;

    @Autowired
    public void setWarehouseService(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public List<Warehouse> select(WarehouseQuery query) {
        return warehouseService.select(query);
    }

    @PostMapping
    public Integer insert(@RequestBody Warehouse warehouse) {
        return warehouseService.insert(warehouse);
    }

    @PutMapping
    public Integer update(@RequestBody Warehouse warehouse) {
        return warehouseService.update(warehouse);
    }

    @DeleteMapping
    public Integer delete(@RequestParam Integer id) {
        return warehouseService.delete(id);
    }
}
