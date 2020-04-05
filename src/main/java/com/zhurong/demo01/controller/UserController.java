package com.zhurong.demo01.controller;

import com.zhurong.demo01.entity.OnePagePara;
import com.zhurong.demo01.entity.PagePara;
import com.zhurong.demo01.entity.ResultObject;
import com.zhurong.demo01.entity.User;
import com.zhurong.demo01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/user",method={RequestMethod.GET,RequestMethod.OPTIONS})
public class UserController {
    @Autowired
    private UserService userservice;
    @RequestMapping(value="/login",method = RequestMethod.POST)
    @ResponseBody
    public Object loginUser(User user) {
        Map<String,Object> result = new HashMap<>();
        String token = userservice.Login(user);
        if(token != null) {
            result.put("code", 200);
            result.put("msg", "登录成功");
            result.put("token", token);
            return result;
        }
        result.put("code", 500);
        result.put("msg", "登录失败");
        return result;
    }


    @RequestMapping(value="/listUser",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject listUser(User user){
        ResultObject res = userservice.ListUser(user);
        return res;
    }




    //对数据库进行查找操作
    @RequestMapping("/ListUserByname")
    @ResponseBody
    public User listUserByname(String name){
        return userservice.findUserByName(name);
    }
    //对数据库进行的删除操作
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public boolean delete(User user) {
        return userservice.delete(user.getCode())>0;
    }
    //对数据进行修改的操作
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(User user) {
        int result = userservice.Update(user);
        if (result >= 1) {
            return "修改成功";
        } else {
            return "修改失败";
        }
    }
    //对数据库进行增加字段操作
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public boolean insert(User user)
    {
        return userservice.insertUser(user) > 0;
    }

    //根据code查询用户数量
    @RequestMapping(value = "/chackUserByCode", method = RequestMethod.POST)
    public boolean chackUserByCode(User user)
    {
        return userservice.chackUserByCode(user) <= 0;
    }

    //在缓存中取用户信息
    @RequestMapping(value = "/getUserFromSession", method = RequestMethod.POST)
    public User getUserFromSession(PagePara para){
        return userservice.getUserFromSession(para.getToken());
    }
}
