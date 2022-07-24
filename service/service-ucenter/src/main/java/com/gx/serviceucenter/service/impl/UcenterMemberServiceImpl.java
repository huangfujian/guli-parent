package com.gx.serviceucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gx.commonutils.JwtUtils;
import com.gx.servicebase.config.exception.GuliException;
import com.gx.serviceucenter.entity.UcenterMember;
import com.gx.serviceucenter.entity.vo.LoginVO;
import com.gx.serviceucenter.entity.vo.RegisterVO;
import com.gx.serviceucenter.mapper.UcenterMemberMapper;
import com.gx.serviceucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gx.serviceucenter.utils.MD5;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author FL
 * @since 2022-06-22
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(LoginVO loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            //都等于空的情况
            throw new GuliException(20001, "账户或密码为空");
        }
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(queryWrapper);
        if (ucenterMember == null) {
            throw new GuliException(20001, "账户不存在，请注册");
        }
        Boolean isDisabled = ucenterMember.getIsDisabled();
        if (isDisabled) {
            throw new GuliException(20001, "该账户已被禁用，请联系后台人员");
        }
        String encrypt = MD5.encrypt(password);
        if (!encrypt.equals(ucenterMember.getPassword())) {
            throw new GuliException(20001, "密码错误");
        }
        String id = ucenterMember.getId();
        String nickname = ucenterMember.getNickname();
        String token = JwtUtils.getJwtToken(id, nickname);
        return token;
    }

    @Override
    public void register(RegisterVO registerVO) {
        String mobile = registerVO.getMobile();
        String nickname = registerVO.getNickname();
        String code = registerVO.getCode();
        String password = registerVO.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname) ||
                StringUtils.isEmpty(code) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "请输入完整的信息");
        }
        //判断验证码是否存在
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (StringUtils.isEmpty(redisCode)) {
            throw new GuliException(20001, "验证码过期,请重新获取");
        }
        //判读验证码是否相同
        if (!code.equals(redisCode)) {
            throw new GuliException(20001, "验证码错误");
        }
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new GuliException(2001, "该账户已存在");
        }
        UcenterMember ucenterMember = new UcenterMember();
        BeanUtils.copyProperties(registerVO, ucenterMember);
        //默认的照片
        ucenterMember.setAvatar("https://gx-guli.oss-cn-guangzhou.aliyuncs.com/%E5%A3%81%E7%BA%B8%E7%B2%BE%E9%80%89%20%2822%29.jpg");
        ucenterMember.setIsDisabled(false);
        ucenterMember.setPassword(MD5.encrypt(password));//加密
        baseMapper.insert(ucenterMember);
    }

    @Override
    public UcenterMember getByOpenId(String openid) {
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        UcenterMember ucenterMember = baseMapper.selectOne(queryWrapper);
        return ucenterMember;
    }

    /**
     * 统计某一天的注册人数
     * @param day
     * @return
     */
    @Override
    public Integer countRegisterByDay(String day) {
        return  baseMapper.selectRegisterCount(day);
    }

}
