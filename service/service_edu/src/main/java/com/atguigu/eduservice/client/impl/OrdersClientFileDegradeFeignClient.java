package com.atguigu.eduservice.client.impl;

import com.atguigu.eduservice.client.OrdersClient;

public class OrdersClientFileDegradeFeignClient implements OrdersClient {
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
