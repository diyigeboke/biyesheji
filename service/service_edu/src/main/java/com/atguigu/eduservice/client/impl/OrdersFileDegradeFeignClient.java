package com.atguigu.eduservice.client.impl;

import com.atguigu.eduservice.client.OrdersClient;

public class OrdersFileDegradeFeignClient implements OrdersClient {
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
