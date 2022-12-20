package com.lab.service;

import com.lab.dto.UserDto;

import java.util.List;

/**
 * @program: DataBoard
 * @className: UserService
 * @description:
 * @author:
 * @create: 2022-12-20 15:54
 * @Version 1.0
 **/
public interface UserService {

    List<UserDto> getUsers();

    UserDto getUser(Long id);
}
