package com.gx.servicecms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gx.commonutils.ResultEntity;
import com.gx.servicebase.config.RedisConfig;
import com.gx.servicecms.entity.CrmBanner;
import com.gx.servicecms.service.CrmBannerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 后端控制器
 * </p>
 *
 * @author FL
 * @since 2022-06-21
 */
@RestController
@RequestMapping("/educms/banner")
@CrossOrigin
@Api("后端管理轮播")
public class BannerAdminController {
    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("/pagination/{pageNum}/{limit}")
    public ResultEntity pagination(@PathVariable("pageNum") Integer pageNum,
                                   @PathVariable("limit") Integer limit) {
        Page<CrmBanner> crmBannerPage = new Page<>(pageNum, limit);
        crmBannerService.page(crmBannerPage, null);
        List<CrmBanner> records = crmBannerPage.getRecords();
        //分页查询
        long total = crmBannerPage.getTotal();
        return ResultEntity.ok().data("items", records).data("total", total);
    }

    @PostMapping("/addBanner")
    public ResultEntity addBanner(@RequestBody CrmBanner crmBanner) {
        crmBannerService.saveBanner(crmBanner);
        return ResultEntity.ok();
    }

    @GetMapping("{id}")
    public ResultEntity getBanner(@PathVariable("id") String id) {
        CrmBanner crmBanner = crmBannerService.getById(id);
        return ResultEntity.ok().data("item", crmBanner);
    }

    @DeleteMapping("{id}")
    public ResultEntity removeBanner(@PathVariable("id") String id) {
        crmBannerService.removeBannerByID(id);
        return ResultEntity.ok();
    }

    @PutMapping("/updateBanner")
    public ResultEntity updateBanner(@RequestBody CrmBanner crmBanner) {
        crmBannerService.updateBanner(crmBanner);
        return ResultEntity.ok();
    }
}

