package com.gx.serviceacl.controller;

import com.alibaba.fastjson.JSONObject;
import com.gx.commonutils.ResultEntity;
import com.gx.serviceacl.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/30 12:47
 */
@RestController
@RequestMapping("/admin/acl/index")
public class IndexController {
    @Autowired
    private IndexService indexService;

    /**
     * 根据token获取用户信息
     *
     * @return
     */
    @GetMapping("info")
    public ResultEntity info() {
        //获取当前登录用户用户名
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(userName);
        return ResultEntity.ok().data(userInfo);
    }
    /**
     * 生成动态路由
     * @return
     */
    @GetMapping("/menu")
    public ResultEntity getMenu() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return ResultEntity.ok().data("permissionList", permissionList);
    }

}
