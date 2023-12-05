package com.rocky.controller;

import com.rocky.model.Vendor;
import com.rocky.model.VendorQuery;
import com.rocky.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {

    private VendorService vendorService;

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public List<Vendor> select(VendorQuery query) {
        return vendorService.select(query);
    }

    @PostMapping
    public Integer insert(@RequestBody Vendor vendor) {
        return vendorService.insert(vendor);
    }

    @PutMapping
    public Integer update(@RequestBody Vendor vendor) {
        return vendorService.update(vendor);
    }

    @DeleteMapping
    public Integer delete(@RequestParam Integer id) {
        return vendorService.delete(id);
    }
}
