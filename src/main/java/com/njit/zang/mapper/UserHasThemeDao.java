package com.njit.zang.mapper;

import com.njit.zang.model.UserHasTheme;
import com.njit.zang.model.UserHasThemeExample;
import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserHasThemeDao {
    long countByExample(UserHasThemeExample example);

    int deleteByExample(UserHasThemeExample example);

    int insert(UserHasTheme record);

    @Delete("delete from user_has_theme where user_uid = #{uid} and theme_name = #{themeName}")
    int deleteUserHasTheme(@Param("uid") String uid,@Param("themeName")String themeName);

    @Select("select theme_name from user_has_theme where user_uid = #{uid}")
    List<String> selectThemeList(@Param("uid") String uid);

    int insertSelective(UserHasTheme record);

    List<UserHasTheme> selectByExample(UserHasThemeExample example);

    int updateByExampleSelective(@Param("record") UserHasTheme record, @Param("example") UserHasThemeExample example);

    int updateByExample(@Param("record") UserHasTheme record, @Param("example") UserHasThemeExample example);
}