package com.tzk.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzk.cms.entity.CrmBanner;
import com.tzk.cms.service.CrmBannerService;
import com.tzk.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器 后台banner管理接口
 * </p>
 *
 * @author zkTang
 * @since 2022-07-25
 */
@SuppressWarnings({"all"})
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController{

    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit ){
        Page<CrmBanner> crmBannerPage = new Page<>(page,limit);
        crmBannerService.page(crmBannerPage,null);
        return R.ok().data("items",crmBannerPage.getRecords()).data("total",crmBannerPage.getTotal());
    }
    @ApiOperation(value = "获取Banner")
    @GetMapping("getBanner/{id}")
    public R getBanner(@PathVariable String id) {
        CrmBanner banner = crmBannerService.getById(id);
        return R.ok().data("item", banner);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner banner) {
        crmBannerService.save(banner);
        return R.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("/updateBanner")
    public R updateBanner(@RequestBody CrmBanner banner) {
        crmBannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("removeBanner/{id}")
    public R removeBanner(@PathVariable String id) {
        crmBannerService.removeById(id);
        return R.ok();
    }
}

