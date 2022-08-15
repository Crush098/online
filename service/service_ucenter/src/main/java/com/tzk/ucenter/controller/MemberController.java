package com.tzk.ucenter.controller;


import com.tzk.commonutils.JwtUtils;
import com.tzk.commonutils.MemberOrder;
import com.tzk.commonutils.R;
import com.tzk.ucenter.entity.Member;
import com.tzk.ucenter.entity.vo.RegisterVo;
import com.tzk.ucenter.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zkTang
 * @since 2022-07-28
 */
@SuppressWarnings({"all"})
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class MemberController {
    @Autowired
    private MemberService memberService;

    //登录
    @PostMapping("/login")
    public R loginUser(@RequestBody Member member){ //RequestBody用Post提交
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }
    //注册
    @PostMapping("/register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }
    //根据token获取用户信息 用于前端页面显示
    @GetMapping("/getMemeberInfo")
    public R getMemeberInfo(HttpServletRequest request){
        //调用Jwt工具类 根据request对象获取头信息 返回用户的id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //根据得到的is值查询用户的信息
        Member member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }

    //根据token字符串获取用户信息
    @PostMapping("/getInfoMemberOrder/{id}")
    public MemberOrder getInfoMemberOrder(@PathVariable String id) {
        //根据用户id获取用户信息
        Member Member = memberService.getById(id);
        MemberOrder memberOrder = new MemberOrder();
        BeanUtils.copyProperties(Member,memberOrder);
        return memberOrder;
    }
    //查询某一天的注册人数
    @GetMapping("/countRegisterDay/{day}")
    public R countRegisterDay(@PathVariable String day){
        Integer count = memberService.countRegisterDay(day);
        return R.ok().data("countRegister",count);
    }
}

