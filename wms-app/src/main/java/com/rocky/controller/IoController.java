package com.rocky.controller;

import com.rocky.model.IoItem;
import com.rocky.model.IoItemQuery;
import com.rocky.model.IoMaster;
import com.rocky.model.IoMasterQuery;
import com.rocky.service.IoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@RestController
@RequestMapping("/api/io")
public class IoController {
    @Autowired
    private IoService ioService;

    @GetMapping
    public List<IoMaster> select(IoMasterQuery query) {
        return ioService.select(query);
    }

    @GetMapping("/item")
    public List<IoItem> select(IoItemQuery query) {
        return ioService.selectItems(query);
    }

    @PostMapping()
    public Integer insert(@RequestBody IoMaster modle) {
        return ioService.insert(modle);
    }
}