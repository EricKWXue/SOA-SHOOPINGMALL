package com.e3mall.cart.service;

import java.util.List;

import com.e3mall.pojo.TbItem;
import com.e3mall.utils.E3Result;

public interface CartService {
	/**
	 * 商品购物车存入redis中
	 * @param userId
	 * @param itemId
	 * @param num
	 * @return
	 */
	E3Result addCart(long userId, long itemId, int num);
	/**
	 * 合并购物车
	 * @param userId
	 * @param itemList
	 * @return
	 */
	E3Result mergeCart(long userId, List<TbItem> itemList);
	/**
	 * 根据用户id取购物车列表
	 * @param userId
	 * @return
	 */
	List<TbItem> getCartList(long userId);
	/**
	 * 更新购物车商品数量
	 * @param userId
	 * @param itemId
	 * @param num
	 * @return
	 */
	E3Result updateCart(long userId, long itemId, int num);
	/**
	 * 删除购物车商品
	 * @param userId
	 * @param itemId
	 * @return
	 */
	E3Result deleteCartItem(long userId, long itemId);
	/**
	 * 清空购物车
	 * @param userId
	 * @return
	 */
	E3Result clearCartItem(long userId);
}
