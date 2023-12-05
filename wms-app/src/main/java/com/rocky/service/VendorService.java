package com.rocky.service;

import com.rocky.dao.VendorDao;
import com.rocky.model.Vendor;
import com.rocky.model.VendorQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {

    @Autowired
    private VendorDao vendorDao;

    public List<Vendor> select(VendorQuery query) {
        return vendorDao.select(query);
    }

    public Integer insert(Vendor vendor) {
        return vendorDao.insert(vendor);
    }

    public Integer update(Vendor vendor) {
        return vendorDao.update(vendor);
    }

    public Integer delete(Integer id) {
        return vendorDao.delete(id);
    }
}