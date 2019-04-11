package com.njit.zang.mapper;

import com.njit.zang.model.Follow;
import com.njit.zang.model.FollowExample;
import java.util.List;

import com.njit.zang.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface FollowDao {
    long countByExample(FollowExample example);

    int deleteByExample(FollowExample example);

    int insert(Follow record);

    int insertSelective(Follow record);

    int delete(@Param("follower")String follower,@Param("master") String master);

    Follow selectByFollow(@Param("follower") String follower,@Param("master") String master);

    List<User> selectByFollower(String follower);

    List<User> selectByMaster(String master);

    List<Follow> selectByExample(FollowExample example);

    int updateByExampleSelective(@Param("record") Follow record, @Param("example") FollowExample example);

    int updateByExample(@Param("record") Follow record, @Param("example") FollowExample example);
}