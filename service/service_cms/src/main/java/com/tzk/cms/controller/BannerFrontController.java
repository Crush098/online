package com.tzk.cms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzk.cms.entity.CrmBanner;
import com.tzk.cms.service.CrmBannerService;
import com.tzk.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author zkTang
 * @since 2022-07-25
 */
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取首页banner")
    @GetMapping("/getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> bannerList = bannerService.selectALLBanner();
        return R.ok().data("bannerList", bannerList);
    }
}

