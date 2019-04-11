package com.njit.zang.mapper;

import com.njit.zang.model.SendContentHasTheme;
import com.njit.zang.model.SendContentHasThemeExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface SendContentHasThemeDao {
    long countByExample(SendContentHasThemeExample example);

    int deleteByExample(SendContentHasThemeExample example);

    int insert(SendContentHasTheme record);

    int insertSelective(SendContentHasTheme record);

    List<String> selectByFeedId(String feedId);

    List<String> selectByTheme(String themeName);

    List<SendContentHasTheme> selectByExample(SendContentHasThemeExample example);

    int updateByExampleSelective(@Param("record") SendContentHasTheme record, @Param("example") SendContentHasThemeExample example);

    int updateByExample(@Param("record") SendContentHasTheme record, @Param("example") SendContentHasThemeExample example);
}