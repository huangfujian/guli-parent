package com.gx.serviceucenter.controller;

import com.google.gson.Gson;
import com.gx.commonutils.JwtUtils;
import com.gx.servicebase.config.exception.GuliException;
import com.gx.serviceucenter.entity.UcenterMember;
import com.gx.serviceucenter.service.UcenterMemberService;
import com.gx.serviceucenter.utils.HttpClientUtils;
import com.gx.serviceucenter.utils.WxOpenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/23 22:45
 */
@CrossOrigin
@Controller
@RequestMapping("/educenter/wx")
public class WxApiController {
    @Autowired
    private WxOpenProperties wxOpenProperties;
    @Autowired
    private UcenterMemberService ucenterMemberService;

    //生成二维码
    @GetMapping("/login")
    public String genQrConnect(HttpSession session) {
        //微信平台授权
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //回调地址
        String redirectUrl = wxOpenProperties.getRedirectUrl();//获取业务服务器重定向地址
        //对地址进行编码
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");//url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(2001, e.getMessage());
        }
        //防止csrf攻击（跨站请求伪造攻击）
        String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        //String state = "guli";///为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        System.out.println("state = " + state);
        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟
        //生成qrcodeUrl
        String qrcodeUrl = String.format(baseUrl, wxOpenProperties.getAppId(), wxOpenProperties.getRedirectUrl(), redirectUrl, state);
        //重定向到该地址
        return "redirect:" + qrcodeUrl;
    }

    //扫描二维码后的回调函数
    @GetMapping("/callback")
    public String callback(String code, String state, HttpSession session) {
        //回调地址获取到微信用户的code(临时票据)
        //在通过临时票据去请求,获取到微信那边的授权
        System.out.println(code);//code
        System.out.println(state);//state
        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        String accessTokenUrl = String.format(baseAccessTokenUrl, wxOpenProperties.getAppId(), wxOpenProperties.getAppSecret(), code);
        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessToken=============");
        } catch (Exception e) {
            throw new GuliException(20001, "获取access_token失败");
        }
        //解析json
        Gson gson = new Gson();
        HashMap hashMap = gson.fromJson(result, HashMap.class);
        String accessToken = (String) hashMap.get("access_token");
        String openid = (String) hashMap.get("openid");
        //查询数据库当前用户是否曾经使用过微信登录
        UcenterMember ucenterMember = ucenterMemberService.getByOpenId(openid);
        if (ucenterMember == null) {
            //不存在的话，就去访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, accessToken, openid);
            //发送请求（远程调用）
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
            } catch (Exception e) {
                throw new GuliException(20001, "获取用户信息失败");
            }
            //解析jsom
            HashMap mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = (String) mapUserInfo.get("nickname");
            String headimgurl = (String) mapUserInfo.get("headimgurl");
            //向数据库插入一跳数据
            ucenterMember = new UcenterMember();
            ucenterMember.setNickname(nickname);
            ucenterMember.setOpenid(openid);
            ucenterMember.setAvatar(headimgurl);
            ucenterMemberService.save(ucenterMember);
        }
        //可以生成一个token返回给前端,但是存在跨域的问题;
        //所以我们这里使用最终解决方案，将token作为请求url地址的参数进行
        //直接返回到主页面
        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return "redirect:http://localhost:3000?token=" + token;
    }
}





















