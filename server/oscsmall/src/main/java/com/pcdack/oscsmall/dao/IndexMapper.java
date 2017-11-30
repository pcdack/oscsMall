package com.pcdack.oscsmall.dao;

import com.pcdack.oscsmall.pojo.Index;

public interface IndexMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Index record);

    int insertSelective(Index record);

    Index selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Index record);

    int updateByPrimaryKey(Index record);
}