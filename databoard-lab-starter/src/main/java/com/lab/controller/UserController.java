package com.lab.controller;

import com.alibaba.fastjson.JSONObject;
import com.lab.service.UserService;
import com.lab.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: DataBoard
 * @className: UserController
 * @description:
 * @author:
 * @create: 2022-12-20 16:15
 * @Version 1.0
 **/
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //获取所有的用户信息
    @GetMapping("/getUserInfoList")
    public String getUserInfoList(){
        return JSONObject.toJSONString(userService.getUsers()
                .stream()
                .map(UserVO::converter)
                .collect(Collectors.toList()));
    }

    //根据id获取用户信息
    @GetMapping("/getUserInfo")
    public String getUserInfo(@RequestParam("id") Long id){
        return JSONObject.toJSONString(UserVO.converter(userService.getUser(id)));
    }
}
