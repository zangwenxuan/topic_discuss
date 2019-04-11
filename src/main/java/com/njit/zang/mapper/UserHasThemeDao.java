package com.njit.zang.mapper;

import com.njit.zang.model.UserHasTheme;
import com.njit.zang.model.UserHasThemeExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface UserHasThemeDao {
    long countByExample(UserHasThemeExample example);

    int deleteByExample(UserHasThemeExample example);

    int insert(UserHasTheme record);

    int insertSelective(UserHasTheme record);

    List<UserHasTheme> selectByExample(UserHasThemeExample example);

    int updateByExampleSelective(@Param("record") UserHasTheme record, @Param("example") UserHasThemeExample example);

    int updateByExample(@Param("record") UserHasTheme record, @Param("example") UserHasThemeExample example);
}