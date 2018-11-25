package com.e3mall.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.cart.service.CartService;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbUser;
import com.e3mall.service.ItemService;
import com.e3mall.utils.CookieUtils;
import com.e3mall.utils.E3Result;
import com.e3mall.utils.JsonUtils;

@Controller
public class CartController {
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CartService cartService;
	
	@Value("${CART_EXPIRE}")
	private Integer CART_EXPIRE;
	/**
	 * 添加购物车
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue="1") Integer num, HttpServletRequest request,HttpServletResponse response){
		//登陆状态
		TbUser user = (TbUser)request.getAttribute("user");
		if(null != user){
			cartService.addCart(user.getId(), itemId, num);
			return "cartSuccess";
		}
		//未登陆状态
		List<TbItem> itemList = getCartList(request);
		boolean hasItem = false;
		for (TbItem tbItem : itemList) {
			if(tbItem.getId() == itemId.longValue()){
				tbItem.setNum(tbItem.getNum()+ num);
				hasItem = true;
				break;
			}
		}
		if(!hasItem){
			TbItem item = itemService.getItemById(itemId);
			item.setNum(num);
			//取一张图片
			String image = item.getImage();
			if (StringUtils.isNotBlank(image)) {
				item.setImage(image.split(",")[0]);
			}
			itemList.add(item);
		}
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(itemList), CART_EXPIRE, true);
		return "cartSuccess";
	}
	/**
	 * 展示购物车
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/cart/cart")
	public String showCart(HttpServletRequest request, HttpServletResponse response, Model model){		
		List<TbItem> itemList = getCartList(request);
		//登陆状态
		TbUser user = (TbUser)request.getAttribute("user");
		if(null != user){
			if (!itemList.isEmpty()) {
				//合并购物车
				cartService.mergeCart(user.getId(), itemList);
				//删除cookie中的购物车
				CookieUtils.setCookie(request, response, "cart", "");
			}			
			itemList = cartService.getCartList(user.getId());
		}
		model.addAttribute("cartList", itemList);
		return "cart";
	}
	/**
	 * 更新购物车商品数量
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public E3Result updateCart(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request,HttpServletResponse response){
		//登陆状态
		TbUser user = (TbUser)request.getAttribute("user");
		if(null != user){
			return cartService.updateCart(user.getId(),itemId,num);
		}
		//未登陆状态
		List<TbItem> itemList = getCartList(request);
		for (TbItem tbItem : itemList) {
			if(tbItem.getId() == itemId.longValue()){
				tbItem.setNum(num);
				break;
			}
		}
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(itemList), CART_EXPIRE, true);
		return E3Result.ok();
	}
	
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request,HttpServletResponse response){
		//登陆状态
		TbUser user = (TbUser)request.getAttribute("user");
		if(null != user){
			cartService.deleteCartItem(user.getId(), itemId);
			return "redirect:/cart/cart.html";
		}
		//未登陆状态
		List<TbItem> itemList = getCartList(request);
		for (TbItem tbItem : itemList) {
			if(tbItem.getId() == itemId.longValue()){
				itemList.remove(tbItem);
				break;
			}
		}
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(itemList), CART_EXPIRE, true);
		return "redirect:/cart/cart.html";
	}
	/**
	 * 从request中取cookie信息
	 * @param request
	 * @return
	 */
	private List<TbItem> getCartList(HttpServletRequest request){
		String jsonList = CookieUtils.getCookieValue(request, "cart", true);
		if(StringUtils.isNotBlank(jsonList)){
			List<TbItem> itemList = JsonUtils.jsonToList(jsonList, TbItem.class);
			return itemList;
		}
		return new ArrayList<>();
	}
}
