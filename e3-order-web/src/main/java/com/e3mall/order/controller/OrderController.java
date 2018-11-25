package com.e3mall.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e3mall.cart.service.CartService;
import com.e3mall.order.pojo.OrderInfo;
import com.e3mall.order.service.OrderService;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbUser;
import com.e3mall.utils.E3Result;

@Controller
public class OrderController {
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request, Model model){
		TbUser user = (TbUser)request.getAttribute("user");
		if(null != user){
			List<TbItem> cartList = cartService.getCartList(user.getId());
			model.addAttribute("cartList", cartList);
		}
		return "order-cart";
	}
	
	@RequestMapping("/order/create")
	public String createOrder(OrderInfo orderInfo, HttpServletRequest request, Model model){
		//取用户信息
		TbUser user = (TbUser) request.getAttribute("user");
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		//提交订单
		E3Result order = orderService.createOrder(orderInfo);
		if(order.getStatus() == 200){
			String orderId = order.getData().toString();
			model.addAttribute("orderId", orderId);
		}
		model.addAttribute("payment", orderInfo.getPayment());
		//清空购物车
		cartService.clearCartItem(user.getId());
		return "success";
	}
}
