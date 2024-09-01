package com.mzl.insta360demo.generator.service.impl;

import com.mzl.insta360demo.generator.entity.Orders;
import com.mzl.insta360demo.generator.mapper.OrdersMapper;
import com.mzl.insta360demo.generator.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author mzl
 * @since 2024-07-28
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}
