package com.e3mall.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.e3mall.easyui.SearchResult;
import com.e3mall.search.service.SearchService;

@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	@Value("${SEARCH_PAGESIZE}")
	private Integer SEARCH_PAGESIZE;
	
	@RequestMapping("/search")
	public String SearchItem(String keyword, @RequestParam(defaultValue="1") Integer page, Model model) throws Exception{
		
		keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
		page = page < 1 ? 1 : page;
		
		SearchResult findSearchItem = searchService.findSearchItem(keyword, page, SEARCH_PAGESIZE);
		model.addAttribute("query", keyword);
		model.addAttribute("page", page);
		model.addAttribute("totalPages", findSearchItem.getTotalPages());
		model.addAttribute("recordCount", findSearchItem.getRecordCount());
		model.addAttribute("itemList", findSearchItem.getItemList());
		return "search";
	}
}
