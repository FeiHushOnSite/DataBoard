package com.lab.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lab.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: DataBoard
 * @interfaceName: UserDao
 * @description:
 * @author:
 * @create: 2022-12-20 15:41
 * @Version 1.0
 **/
@Mapper
public interface UserDao extends BaseMapper<User> {
}
