package com.rocky.controller;

import com.rocky.model.Inventory;
import com.rocky.model.InventoryQuery;
import com.rocky.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService Service;

    @GetMapping
    public List<Inventory> select(InventoryQuery query) {
        return Service.select(query);
    }

}
