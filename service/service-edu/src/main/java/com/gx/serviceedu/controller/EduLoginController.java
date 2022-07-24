package com.gx.serviceedu.controller;

import com.gx.commonutils.ResultEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/16 14:50
 */
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {
    @PostMapping("/login")
    public ResultEntity login() {
        return ResultEntity.ok().data("token", "admin");
    }

    @GetMapping("/info")
    public ResultEntity info() {
        return ResultEntity.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "");
    }

}
