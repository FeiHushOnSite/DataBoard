package com.lab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lab.dao.UserDao;
import com.lab.dto.UserDto;
import com.lab.entity.User;
import com.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: DataBoard
 * @className: UserServiceImpl
 * @description:
 * @author:
 * @create: 2022-12-20 15:55
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<UserDto> getUsers() {
        QueryWrapper<User> userQuery = new QueryWrapper<>();
        return userDao.selectList(userQuery).stream()
                .map(UserDto::converter)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(Long id) {
        QueryWrapper<User> userQuery = new QueryWrapper<>();
        userQuery.eq("id", id);
        return UserDto.converter(userDao.selectOne(userQuery));
    }
}
