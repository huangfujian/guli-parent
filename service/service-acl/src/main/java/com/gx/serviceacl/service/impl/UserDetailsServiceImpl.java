package com.gx.serviceacl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gx.security.entity.SecurityUser;
import com.gx.serviceacl.entity.Permission;
import com.gx.serviceacl.entity.User;
import com.gx.serviceacl.service.PermissionService;
import com.gx.serviceacl.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/30 12:20
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 根据用户名加载用户
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从数据库中取出用户信息
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User user = userService.getOne(userQueryWrapper);
        //判断用户是否存在
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        com.gx.security.entity.User curUser = new com.gx.security.entity.User();
        BeanUtils.copyProperties(user, curUser);
        //查询出当前用户的全部权限
        List<String> authorities = permissionService.selectPermissionValueByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser(curUser);
        securityUser.setPermissionValueList(authorities);//将权限设置进去
        return securityUser;//返回用户
    }
}
