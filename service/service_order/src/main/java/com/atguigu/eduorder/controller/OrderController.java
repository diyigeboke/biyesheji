package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-11
 */
@RestController
@RequestMapping("/eduorder/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //1 生成订单的方法
    @PostMapping("/createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){
        //创建订单，返回订单号
        String orderNo = orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",orderNo);
    }

    //2 根据订单id查询订单信息
    @GetMapping("/getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder TOrder = orderService.getOne(wrapper);
        return R.ok().data("item", TOrder);
    }

    //根据课程id和用户id查询订单表中订单状态
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,
                               @PathVariable String memberId){
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        if(count > 0){ //已经支付
            return true;
        }else {
            return false;
        }
    }
}
