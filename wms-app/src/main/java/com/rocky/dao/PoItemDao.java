package com.rocky.dao;

import com.gmw.annotation.PageX;
import com.rocky.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PoItemDao {

    @PageX
    List<PoItem> select(PoItemQuery query);

    int insert(PoItem model);

    int insertList(List<PoItem> poitems);

    int update(PoItem model);

    int delete(Integer id);
}
