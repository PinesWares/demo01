package com.zhurong.demo01.mapper;

import com.zhurong.demo01.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    User findUserByName(String code);
    List<User> ListUser(User user);
    int insertUser(User user);
    int delete(String code);
    int Update(User user);
    int chackUserByCode(User user);
}
