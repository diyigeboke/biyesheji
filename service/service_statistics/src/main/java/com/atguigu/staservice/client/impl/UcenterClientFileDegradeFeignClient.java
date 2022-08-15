package com.atguigu.staservice.client.impl;

import com.atguigu.commonutils.R;
import com.atguigu.staservice.client.UcenterClient;

public class UcenterClientFileDegradeFeignClient implements UcenterClient {
    @Override
    public R countRegister(String day) {
        return R.error().message("查询不到数据");
    }
}
