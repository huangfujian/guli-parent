package com.gx.servicecms.service;

import com.gx.servicecms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author FL
 * @since 2022-06-21
 */
public interface CrmBannerService extends IService<CrmBanner> {
    List<CrmBanner> selectIndexList();

    void saveBanner(CrmBanner crmBanner);

    void removeBannerByID(String id);

    void updateBanner(CrmBanner crmBanner);
}
