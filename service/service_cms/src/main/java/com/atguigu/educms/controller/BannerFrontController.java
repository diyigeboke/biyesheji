package com.atguigu.educms.controller;

import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    //查询所有banner
    @GetMapping("/getAllBanner")
    public R getAllBanner(){
        //自己写一个方法方便后面加redis
        //根据id进行降序排列,显示排列之后前两条记录
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //last方法,sql语句拼接
        wrapper.last("limit 2");
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }

}
