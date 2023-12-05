package com.rocky.dao;

import com.gmw.annotation.PageX;
import com.rocky.model.Vendor;
import com.rocky.model.VendorQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VendorDao {
    @PageX
    List<Vendor> select(VendorQuery query);

    int insert(Vendor vendor);

    int update(Vendor vendor);

    int delete(Integer id);
}