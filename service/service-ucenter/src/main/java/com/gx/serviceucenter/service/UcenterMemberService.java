package com.gx.serviceucenter.service;

import com.gx.serviceucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gx.serviceucenter.entity.vo.LoginVO;
import com.gx.serviceucenter.entity.vo.RegisterVO;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author FL
 * @since 2022-06-22
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVO loginVo);

    void register(RegisterVO registerVO);

    UcenterMember getByOpenId(String openid);

    Integer countRegisterByDay(String day);

}
