package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-08-11
 */
public interface OrderService extends IService<TOrder> {

    //创建订单，返回订单号
    String createOrders(String courseId, String memberId);

}
