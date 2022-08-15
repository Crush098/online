package com.tzk.ucenter.mapper;

import com.tzk.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author zkTang
 * @since 2022-07-28
 */
public interface MemberMapper extends BaseMapper<Member> {
    //查詢某一天注冊人数
    Integer countRegisterDay(String day);
}
