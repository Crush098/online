package com.tzk.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzk.cms.entity.CrmBanner;
import com.tzk.cms.mapper.CrmBannerMapper;
import com.tzk.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author zkTang
 * @since 2022-07-25
 */
@SuppressWarnings({"all"})
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(key = "'selectIndexList'",value = "banner")
    @Override
    public List<CrmBanner> selectALLBanner() {
        QueryWrapper<CrmBanner> crmBannerQueryWrapper = new QueryWrapper<>();
        crmBannerQueryWrapper.orderByDesc("id");
        crmBannerQueryWrapper.last("limit 2"); //拼接sql语句
        List<CrmBanner> crmBanners = baseMapper.selectList(crmBannerQueryWrapper);
        return crmBanners;
    }
}
