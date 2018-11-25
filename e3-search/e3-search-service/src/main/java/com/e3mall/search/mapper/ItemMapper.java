package com.e3mall.search.mapper;

import java.util.List;

import com.e3mall.easyui.SearchItem;

public interface ItemMapper {
	List<SearchItem> getAllSearchItem();
	
	SearchItem getItemById(long itemId);
}