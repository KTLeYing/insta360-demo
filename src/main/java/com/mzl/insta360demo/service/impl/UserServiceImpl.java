package com.mzl.insta360demo.service.impl;

import com.mzl.insta360demo.entity.User;
import com.mzl.insta360demo.infrastructure.mapper.UserMapper;
import com.mzl.insta360demo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author mzl
 * @since 2023-12-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
