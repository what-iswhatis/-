package com.rocky.dao;

import com.gmw.annotation.PageX;
import com.rocky.model.Warehouse;
import com.rocky.model.WarehouseQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WarehouseDao {
    @PageX
    List<Warehouse> select(WarehouseQuery query);

    int insert(Warehouse warehouse);

    int update(Warehouse warehouse);

    int delete(Integer id);
}