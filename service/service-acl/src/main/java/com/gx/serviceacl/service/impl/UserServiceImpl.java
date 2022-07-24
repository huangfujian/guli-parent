package com.gx.serviceacl.service.impl;

import com.gx.serviceacl.entity.User;
import com.gx.serviceacl.mapper.UserMapper;
import com.gx.serviceacl.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author FL
 * @since 2022-06-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
