package com.njit.zang.mapper;

import com.njit.zang.model.Pictures;
import com.njit.zang.model.PicturesExample;

import java.util.LinkedList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface PicturesDao {
    long countByExample(PicturesExample example);

    int deleteByExample(PicturesExample example);

    int insert(Pictures record);

    int insertSelective(Pictures record);

    List<String> selectByFeedId(String feedId);

    List<Pictures> selectByExample(PicturesExample example);

    int updateByExampleSelective(@Param("record") Pictures record, @Param("example") PicturesExample example);

    int updateByExample(@Param("record") Pictures record, @Param("example") PicturesExample example);
}