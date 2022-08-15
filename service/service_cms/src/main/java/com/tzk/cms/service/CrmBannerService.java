package com.tzk.cms.service;

import com.tzk.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author zkTang
 * @since 2022-07-25
 */

public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectALLBanner();
}
