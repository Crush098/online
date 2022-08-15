package com.tzk.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzk.commonutils.JwtUtils;
import com.tzk.commonutils.MD5;
import com.tzk.servicebase.exceptionhandle.CustomException;
import com.tzk.ucenter.entity.Member;
import com.tzk.ucenter.entity.vo.RegisterVo;
import com.tzk.ucenter.mapper.MemberMapper;
import com.tzk.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * @author zkTang
 * @since 2022-07-28
 */
@SuppressWarnings({"all"})
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    //用户登录
    public String login(Member member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new CustomException(20001,"密码和手机号不能为空");
        }
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("mobile",mobile);
        Member mobileMember = baseMapper.selectOne(memberQueryWrapper);

        if (mobileMember == null){
            throw new CustomException(20001,"手机号不能为空，登陆失败");
        }
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new CustomException(20001,"密码不能为空，登陆失败");
        }
        if (mobileMember.getIsDeleted()){
            throw new CustomException(20001,"用户不存在，登陆失败");
        }
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }

    @Override
    //用户注册
    public void register(RegisterVo registerVo) {
        System.out.println(registerVo);
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickName = registerVo.getNickname();
        String password = registerVo.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(nickName) || StringUtils.isEmpty(code)){
            throw new CustomException(20001,"注册失败");
        }
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)){
            throw new CustomException(20001,"验证码错误");
        }
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(memberQueryWrapper);
        if (count > 0){
            throw new CustomException(20001,"注册失败");
        }
        Member member = new Member();
        member.setMobile(mobile);member.setNickname(nickName);
        member.setPassword(MD5.encrypt(password));member.setIsDisabled(false);
        baseMapper.insert(member);
    }

    @Override
    public Member getByOpenid(String openid) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("openid",openid);
        Member member = baseMapper.selectOne(memberQueryWrapper);
        return member;
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
