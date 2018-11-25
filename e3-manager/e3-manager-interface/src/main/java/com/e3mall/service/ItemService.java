package com.e3mall.service;

import com.e3mall.easyui.DataGridResult;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemDesc;
import com.e3mall.utils.E3Result;

public interface ItemService {
	/**
	 * 根据id获取商品信息
	 * @param itemId
	 * @return
	 */
	TbItem getItemById(Long itemId);
	/**
	 * 根据id获取商品描述
	 * @param itemId
	 * @return
	 */
	TbItemDesc getItemDescById(Long itemId);
	
	/**
	 * 获取商品列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	DataGridResult getItemList(int pageNo, int pageSize);
	
	/**
	 * 插入商品
	 * @param item
	 * @param desc
	 * @return
	 */
	E3Result addItem(TbItem item, String desc);
		
}
