package com.njit.zang.mapper;

import com.njit.zang.model.Theme;
import com.njit.zang.model.ThemeExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ThemeDao {
    long countByExample(ThemeExample example);

    int deleteByExample(ThemeExample example);

    int insert(Theme record);

    int insertSelective(Theme record);

    String selectByThemeName(String themeName);

    @Select("select theme_name from theme where theme_name LIKE #{themeName}")
    List<String> searchTheme(String themeName);

    List<Theme> selectByExample(ThemeExample example);

    int updateByExampleSelective(@Param("record") Theme record, @Param("example") ThemeExample example);

    int updateByExample(@Param("record") Theme record, @Param("example") ThemeExample example);
}