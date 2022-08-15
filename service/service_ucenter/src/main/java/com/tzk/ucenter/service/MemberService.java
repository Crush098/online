package com.tzk.ucenter.service;

import com.tzk.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tzk.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zkTang
 * @since 2022-07-28
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void register(RegisterVo registerVo);

    Member getByOpenid(String openid);

    Integer countRegisterDay(String day);
}
