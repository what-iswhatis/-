package com.rocky.dao;

import com.gmw.annotation.PageX;
import com.rocky.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PoMasterDao {

    @PageX
    List<PoMaster> select(PoMasterQuery query);

    int insert(PoMaster model);

    int update(PoMaster model);

    int delete(Integer id);
}
