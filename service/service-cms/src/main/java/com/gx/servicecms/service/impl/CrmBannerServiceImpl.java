package com.gx.servicecms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gx.servicecms.entity.CrmBanner;
import com.gx.servicecms.mapper.CrmBannerMapper;
import com.gx.servicecms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author FL
 * @since 2022-06-21
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
    //读取缓存,用于查询缓存数据的使用
    //vakue::key拼接成redis中的key
    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> selectIndexList() {
        QueryWrapper<CrmBanner> queryWrapper = new QueryWrapper<>();
        //进行倒序，显示前两条
        queryWrapper.orderByDesc("id").last("limit 2");
        List<CrmBanner> crmBanners = baseMapper.selectList(queryWrapper);
        return crmBanners;
    }

    @CacheEvict(value = "banner", allEntries = true) //所有条目清除
    @Override
    public void saveBanner(CrmBanner crmBanner) {
        baseMapper.insert(crmBanner);
    }

    @CacheEvict(value = "banner", allEntries = true)//清除所有条目
    @Override
    public void removeBannerByID(String id) {
        baseMapper.deleteById(id);
    }

    @CacheEvict(value = "banner", allEntries = true)//清除所有条目
    @Override
    public void updateBanner(CrmBanner crmBanner) {
        baseMapper.updateById(crmBanner);
    }

}
