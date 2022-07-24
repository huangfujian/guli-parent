package com.gx.serviceacl.service;

import com.gx.serviceacl.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author FL
 * @since 2022-06-28
 */
public interface RoleService extends IService<Role> {
    List<Role> selectRoleByUserId(String id);

    Map<String, Object> findRoleByUserId(String userId);

    void saveUserRoleRealtionShip(String userId, String[] roleId);

}
