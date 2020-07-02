package org.gv_data.hmt.mapper;

import org.apache.ibatis.annotations.Param;
import org.gv_data.hmt.model.User;
import org.gv_data.hmt.model.Role;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User loadUserByUsername(String username);

    List<Role> getHrRolesById(Integer id);

    List<User> getAllHrs(@Param("hrid") Integer hrid, @Param("keywords") String keywords);

    List<User> getAllHrsExceptCurrentHr(Integer id);

    Integer updatePasswd(@Param("hrid") Integer hrid, @Param("encodePass") String encodePass);

    Integer updateUserface(@Param("url") String url, @Param("id") Integer id);
}