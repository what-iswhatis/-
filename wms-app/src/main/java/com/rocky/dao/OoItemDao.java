package com.rocky.dao;

import com.gmw.annotation.PageX;
import com.rocky.model.OoItem;
import com.rocky.model.OoItemQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OoItemDao {
    @PageX
    List<OoItem> select(OoItemQuery query);

    int insert(List<OoItem> items);


}