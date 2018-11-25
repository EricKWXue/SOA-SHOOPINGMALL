package com.e3mall.search.service;

import com.e3mall.easyui.SearchResult;

public interface SearchService {
	/**
	 * 根据关键词查找商品
	 * @param keyword
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public SearchResult findSearchItem(String keyword, Integer pageNo, Integer pageSize) throws Exception;
}
