package com.gx.serviceacl.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gx.commonutils.MD5;
import com.gx.commonutils.ResultEntity;
import com.gx.serviceacl.entity.User;
import com.gx.serviceacl.service.RoleService;
import com.gx.serviceacl.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取管理用户分页列表")
    @GetMapping("{page}/{limit}")
    public ResultEntity index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    User userQueryVo) {
        Page<User> pageParam = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(userQueryVo.getUsername())) {
            wrapper.like("username", userQueryVo.getUsername());
        }

        IPage<User> pageModel = userService.page(pageParam, wrapper);
        return ResultEntity.ok().data("items", pageModel.getRecords()).data("total", pageModel.getTotal());
    }

    @ApiOperation(value = "新增管理用户")
    @PostMapping("save")
    public ResultEntity save(@RequestBody User user) {
        user.setPassword(MD5.encrypt(user.getPassword()));
        userService.save(user);
        return ResultEntity.ok();
    }

    @ApiOperation(value = "修改管理用户")
    @PutMapping("update")
    public ResultEntity updateById(@RequestBody User user) {
        userService.updateById(user);
        return ResultEntity.ok();
    }

    @ApiOperation(value = "删除管理用户")
    @DeleteMapping("remove/{id}")
    public ResultEntity remove(@PathVariable("id") String id) {
        userService.removeById(id);
        return ResultEntity.ok();
    }

    @ApiOperation(value = "根据id列表删除管理用户")
    @DeleteMapping("batchRemove")
    public ResultEntity batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds(idList);
        return ResultEntity.ok();
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public ResultEntity toAssign(@PathVariable("userId") String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return ResultEntity.ok().data(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public ResultEntity doAssign(@RequestParam("userId") String userId, @RequestParam("roleId") String[] roleId) {
        roleService.saveUserRoleRealtionShip(userId, roleId);
        return ResultEntity.ok();
    }

    @ApiOperation(value = "根据用户id获取用户信息")
    @GetMapping("/get/{id}")
    public ResultEntity getById(@PathVariable("id") String id) {
        User user = userService.getById(id);
        return ResultEntity.ok().data("item", user);
    }
}

