package com.e3mall.content.service;

import java.util.List;

import com.e3mall.easyui.DataGridResult;
import com.e3mall.pojo.TbContent;
import com.e3mall.utils.E3Result;

public interface ContentService {
	/**
	 * 展示内容列表
	 * @param id
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	DataGridResult getContentList(Long categoryId, int pageNo, int pageSize);
	/**
	 * 添加内容
	 * @param content
	 * @return
	 */
	E3Result addContent(TbContent content);
	/**
	 * 根据分类id获取内容信息
	 * @param categoryId
	 * @return
	 */
	List<TbContent> getContentListByCatId(Long categoryId);
}
