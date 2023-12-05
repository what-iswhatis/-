package com.rocky.controller;

import cn.hutool.core.date.DateUtil;
import com.gmw.threadlocal.LocalUserUtil;
import com.rocky.model.*;
import com.rocky.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/po")
public class PoController {
    @Autowired
    private PoService poService;

    @GetMapping
    public List<PoMaster> select(PoMasterQuery query) {
        return poService.select(query);
    }

    @GetMapping("/sendemail")
    public Integer sendMail(String id) {
        return poService.sendMail(id);
    }

    @PostMapping()
    //@PreAuthorize("hasAnyRole('purchaser','purchaser.manager','admin')")
    public Integer insert(@RequestBody PoMaster master) throws Exception {

        return poService.insert(master);
    }

    @PutMapping
    public Integer update(@RequestBody PoMaster modle) {
        return poService.update(modle);
    }

    @DeleteMapping
    public Integer delete(@RequestParam Integer id) {
        return poService.delete(id);
    }

    @GetMapping("/item")
    public List<PoItem> select(PoItemQuery query) {
        return poService.selectItems(query);
    }
}
