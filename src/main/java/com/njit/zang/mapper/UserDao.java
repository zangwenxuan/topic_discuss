package com.njit.zang.mapper;

import com.njit.zang.model.User;
import com.njit.zang.model.UserExample;
import java.util.List;

import com.njit.zang.model.UserSendContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.PostMapping;

@Mapper
public interface UserDao {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(String uid);

    int insert(User record);

    int insertSelective(User record);

    @Select("select * from `user` where nickname = #{name}")
    User checkName(@Param("name") String name);

    @Select("select * from `user` where email = #{email}")
    User checkEmail(@Param("email") String email);

    @Select("select * from `user` where nickname = #{nickname}")
    User selectUserByName(@Param("nickname")String nickname);

    @Select("select * from `user` where email = #{email}")
    User selectUserByEmail(@Param("email") String email);

    @Update("update user set avatar = #{avatar} where uid = #{uid} ")
    int updateAvatar(@Param("avatar") String avatar,@Param("uid") String uid);

    @Update("update user set cover = #{cover} where uid = #{uid} ")
    int updateCover(@Param("cover") String cover,@Param("uid") String uid);

    @Update("update user set signature = #{signature} where uid = #{uid} ")
    int updateSignature(@Param("signature") String signature,@Param("uid") String uid);


    List<User> selectAllUsers();

    UserSendContent selectContentByFeedId(String feedId);

    List<UserSendContent> selectContentByUid(String uid);

    List<UserSendContent> selectAllContent();

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(String uid);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}