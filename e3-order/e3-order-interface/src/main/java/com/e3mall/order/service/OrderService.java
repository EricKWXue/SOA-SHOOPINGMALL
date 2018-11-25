package com.e3mall.order.service;

import com.e3mall.order.pojo.OrderInfo;
import com.e3mall.utils.E3Result;

public interface OrderService {
	/**
	 * 创建订单
	 * @param orderInfo
	 * @return
	 */
	E3Result createOrder(OrderInfo orderInfo);
}
