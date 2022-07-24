package com.gx.serviceucenter.controller;

import com.gx.commonutils.JwtUtils;
import com.gx.commonutils.ResultEntity;
import com.gx.commonutils.vo.UcenterMemberVO;
import com.gx.serviceucenter.entity.UcenterMember;
import com.gx.serviceucenter.entity.vo.LoginVO;
import com.gx.serviceucenter.entity.vo.RegisterVO;
import com.gx.serviceucenter.entity.vo.ShowMemberVO;
import com.gx.serviceucenter.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author FL
 * @since 2022-06-22
 */
@RestController
@CrossOrigin
@RequestMapping("/educenter/ucentermember")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    //登录
    @PostMapping("/login")
    public ResultEntity login(@RequestBody LoginVO loginVo) {
        String token = ucenterMemberService.login(loginVo);
        return ResultEntity.ok().data("token", token);
    }

    //注册
    @PostMapping("/register")
    public ResultEntity register(@RequestBody RegisterVO registerVO) {
        ucenterMemberService.register(registerVO);
        return ResultEntity.ok();
    }

    //登录
    @ApiOperation("获取登录信息")
    @GetMapping("/getLoginInfo")
    public ResultEntity getLoginInfo(HttpServletRequest request) {
        String id = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        ShowMemberVO showMemberVO = new ShowMemberVO();
        BeanUtils.copyProperties(ucenterMember, showMemberVO);
        return ResultEntity.ok().data("item", showMemberVO);
    }

    @GetMapping("/getUcenterMember/{id}")
    @ApiOperation("远程调用获取会员信息")
    public UcenterMemberVO getUcenterMember(@PathVariable("id") String id) {
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        UcenterMemberVO ucenterMemberVO = new UcenterMemberVO();
        BeanUtils.copyProperties(ucenterMember, ucenterMemberVO);
        return ucenterMemberVO;
    }

    /**
     * 根据日期,统计某一天的注册的人数
     * @param day
     * @return
     */
    @ApiOperation("统计某一天注册的人数")
    @GetMapping("/registerCount/{day}")
    public ResultEntity registerCount(@PathVariable("day") String day) {
        Integer count = ucenterMemberService.countRegisterByDay(day);
        return ResultEntity.ok().data("countRegister", count);
    }
}

