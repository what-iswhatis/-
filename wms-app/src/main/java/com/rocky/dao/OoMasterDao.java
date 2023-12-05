package com.rocky.dao;

import com.gmw.annotation.PageX;
import com.rocky.model.IoMaster;
import com.rocky.model.IoMasterQuery;
import com.rocky.model.OoMaster;
import com.rocky.model.OoMasterQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OoMasterDao {
    @PageX
    List<OoMaster> select(OoMasterQuery query);

    int insert(OoMaster model);
}