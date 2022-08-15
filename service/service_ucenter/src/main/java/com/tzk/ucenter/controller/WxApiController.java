package com.tzk.ucenter.controller;

import com.google.gson.Gson;
import com.tzk.commonutils.JwtUtils;
import com.tzk.servicebase.exceptionhandle.CustomException;
import com.tzk.ucenter.entity.Member;
import com.tzk.ucenter.service.MemberService;
import com.tzk.ucenter.utils.ConstantWxUtils;
import com.tzk.ucenter.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@SuppressWarnings({"all"})
@CrossOrigin
@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @GetMapping("/callback")
    public String callback(String code, String state, HttpSession session) {

        //得到授权临时票据code
        System.out.println("code = " + code);
        System.out.println("state = " + state);
        //从redis中将state获取出来，和当前传入的state作比较
        //如果一致则放行，如果不一致则抛出异常：非法访问

        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_APP_SECRET,
                code);

        String accessTokenInfo = null;
        try {
            accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessToken=============" + accessTokenInfo);
        } catch (Exception e) {
            throw new CustomException(20001, "获取access_token失败");
        }

        //解析json字符串 利用gson转为map集合 取出里面的值
        Gson gson = new Gson();
        HashMap map = gson.fromJson(accessTokenInfo, HashMap.class);
        String accessToken = (String)map.get("access_token");
        String openid = (String)map.get("openid");

        //查询数据库当前用用户是否曾经使用过微信登录
        Member member = memberService.getByOpenid(openid);
        if(member == null){
            System.out.println("新用户注册");
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("resultUserInfo==========" + resultUserInfo);
            } catch (Exception e) {
                throw new CustomException(20001, "获取用户信息失败");
            }

            //解析json
            HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = (String)mapUserInfo.get("nickname");
            String headimgurl = (String)mapUserInfo.get("headimgurl");

            //向数据库中插入一条记录
            member = new Member();
            member.setNickname(nickname);
            member.setOpenid(openid);
            member.setAvatar(headimgurl);
            memberService.save(member);
        }

        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

        //TODO 登录
        return "redirect:http://localhost:3000?token="+jwtToken;
    }


    @GetMapping("/login")
    public String genQrConnect(HttpSession session) {

        // 微信开放平台授权baseUrl  %s相当于问号 占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new CustomException(20001,"出现错误");
        }

        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        //String state = "imhelen";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        //System.out.println("state = " + state);

        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "tzk");

        return "redirect:" + qrcodeUrl;
    }
}
