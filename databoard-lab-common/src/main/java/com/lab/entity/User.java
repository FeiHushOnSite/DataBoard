package com.lab.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @program: DataBoard
 * @className: User
 * @description: db_user
 * @author:
 * @create: 2022-12-20 15:46
 * @Version 1.0
 **/
@Data
@TableName("db_user")
public class User {

    private Long id;

    private String name;

    private String email;

    private String password;

    private Date dbCreate;

    private Date dbModified;
}
