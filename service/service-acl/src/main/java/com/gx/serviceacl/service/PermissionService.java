package com.gx.serviceacl.service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.gx.serviceacl.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gx.serviceacl.vo.PermissionVO;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author FL
 * @since 2022-06-28
 */
public interface PermissionService extends IService<Permission> {

    List<PermissionVO>  selectPermissionAll();

    void removeChildById(String id);

    void saveRolePermissionRealtionShip(String roleId, String[] permissionIds);

    List<String> selectPermissionValueByUserId(String id);

    List<JSONObject> selectPermissionByUserId(String id);

    List<String> selectPermissionByRoleId(String roleId);

}
