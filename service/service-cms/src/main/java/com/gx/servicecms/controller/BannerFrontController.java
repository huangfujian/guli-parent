package com.gx.servicecms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gx.commonutils.ResultEntity;
import com.gx.servicecms.entity.CrmBanner;
import com.gx.servicecms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/21 22:17
 */
@RestController
@RequestMapping("/educms/banner")
@Api("网站首页列表")
@CrossOrigin //跨域
public class BannerFrontController {
    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("/getAllBanner")
    @ApiOperation(value = "获取首页banner")
    public ResultEntity getAllBanner() {
        List<CrmBanner> crmBanners = crmBannerService.selectIndexList();
        return ResultEntity.ok().data("items", crmBanners);
    }
}
