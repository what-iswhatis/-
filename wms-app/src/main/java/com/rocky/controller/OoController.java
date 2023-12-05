package com.rocky.controller;

import com.rocky.model.*;
import com.rocky.service.OoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oo")
public class OoController {

    private OoService ooService;

    @Autowired
    public void setOoService(OoService ooService) {
        this.ooService = ooService;
    }

    @GetMapping
    public List<OoMaster> select(OoMasterQuery query) {
        return ooService.select(query);
    }

    @GetMapping("/item")
    public List<OoItem> select(OoItemQuery query) {
        return ooService.selectItems(query);
    }

    @PostMapping
    public Integer insert(@RequestBody OoMaster master) {
        return ooService.insert(master);
    }


}
