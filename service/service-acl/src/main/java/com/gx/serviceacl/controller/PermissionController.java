package com.gx.serviceacl.controller;

import com.gx.commonutils.ResultEntity;
import com.gx.serviceacl.entity.Permission;
import com.gx.serviceacl.service.PermissionService;
import com.gx.serviceacl.vo.PermissionVO;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author FL
 * @since 2022-06-28
 */
@RestController
@RequestMapping("/admin/acl/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    /**
     * 查询全部的菜单
     *
     * @return
     */
    @ApiOperation("查询全部菜单")
    @GetMapping("/getAll")
    public ResultEntity indexAllPermission() {
        List<PermissionVO> list = permissionService.selectPermissionAll();
        return ResultEntity.ok().data("item", list);
    }
    /**
     * 批量删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/remove/{id}")
    @ApiOperation("批量删除")
    public ResultEntity removePermission(@PathVariable("id") String id) {
        permissionService.removeChildById(id);
        return ResultEntity.ok();
    }
    /**
     * 执行分配
     *
     * @param roleId
     * @param permissionIds
     * @return
     */
    @ApiOperation("分配")
    @PostMapping("/doAssign")
    public ResultEntity doAssign(@RequestParam("roleId") String roleId, @RequestParam("permissionIds") String[] permissionIds) {
        permissionService.saveRolePermissionRealtionShip(roleId, permissionIds);
        return ResultEntity.ok();
    }
    /**
     * 根据角色获取id
     *
     * @param roleId
     * @return
     */
    @GetMapping("/toAssign/{roleId}")
    @ApiOperation("根据角色获取Id")
    public ResultEntity toAssign(@PathVariable("roleId") String roleId) {
        List<String> list = permissionService.selectPermissionByRoleId(roleId);
        return ResultEntity.ok().data("item", list);
    }
    @PostMapping("/save")
    @ApiOperation("新增菜单")
    public ResultEntity save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return ResultEntity.ok();
    }
    @ApiOperation("修改菜单")
    @PutMapping("/update")
    public ResultEntity update(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return ResultEntity.ok();
    }
}

