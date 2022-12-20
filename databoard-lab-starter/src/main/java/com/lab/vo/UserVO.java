package com.lab.vo;

import com.lab.dto.UserDto;
import com.lab.entity.User;
import lombok.Builder;
import lombok.Data;

/**
 * @program: DataBoard
 * @className: UserVO
 * @description:
 * @author:
 * @create: 2022-12-20 16:17
 * @Version 1.0
 **/
@Data
@Builder
public class UserVO {

    private Long id;

    private String name;

    private String email;

    public static UserVO converter(UserDto userDto) {
        return UserVO.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }
}
