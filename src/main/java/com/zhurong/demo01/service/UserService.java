package com.zhurong.demo01.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhurong.demo01.common.CookieUtil;
import com.zhurong.demo01.entity.OnePagePara;
import com.zhurong.demo01.entity.ResultObject;
import com.zhurong.demo01.entity.User;
import com.zhurong.demo01.mapper.UserMapper;
import com.zhurong.demo01.common.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Repository
public class UserService {
    private static String REDIS_SESSION_KEY = "SessionCache";
    private static final long SESSION_EXPIRE = 7200;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;
    public User findUserByName(String name){
        return userMapper.findUserByName(name);
    }
    public ResultObject ListUser(User user){
        int currentPage = user.getCurrentPage();
        int pageSize = user.getPageSize();
        user.setCurrentPage((currentPage - 1) * pageSize);
        List<User> userList = userMapper.ListUser(user);
        ResultObject objRes = new  ResultObject();
        int dataCount = 0;
        if(userList.size()>0){
            dataCount = userList.get(0).getDataCount();
        }
        objRes.setData(userList);
        objRes.setDataCount(dataCount);
        return objRes;
    }
    public int insertUser(User user){
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        return userMapper.insertUser(user);
    }
    public int delete(String code){
        return userMapper.delete(code);
    }
    public int Update(User user){
        return userMapper.Update(user);
    }
    public String Login(User user){
        User userResult = findUserByName(user.getCode());
        if(userResult != null){
            String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            if(password.equals(userResult.getPassword())) {
                String token = UUID.randomUUID().toString();
                //把用户信息写入redis
                //key:REDIS_SESSION:{TOKEN}
                //value:user转成json
                userResult.setPassword(null);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    redisUtil.set(REDIS_SESSION_KEY+":"+token, mapper.writeValueAsString(userResult));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //设置session过期时间
                redisUtil.expire(REDIS_SESSION_KEY+":"+token, SESSION_EXPIRE);
                return token;
            }
        }
        return null;
    }
    public int chackUserByCode(User user){
        int res = userMapper.chackUserByCode(user);
        return res;
    }
    public User getUserFromSession(String token) {
        String res = redisUtil.get(REDIS_SESSION_KEY+":"+token).toString();
        ObjectMapper mapper = new ObjectMapper();
        User user = null;
        try {
            user = mapper.readValue(res,User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
