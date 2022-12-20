package com.lab.dto;

import com.lab.entity.User;
import lombok.Builder;
import lombok.Data;

/**
 * @program: DataBoard
 * @className: UserDto
 * @description:
 * @author:
 * @create: 2022-12-20 16:26
 * @Version 1.0
 **/
@Data
@Builder
public class UserDto {

    private Long id;

    private String name;

    private String email;

    public static UserDto converter(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
