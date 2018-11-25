package com.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.content.service.ContentService;
import com.e3mall.easyui.DataGridResult;
import com.e3mall.pojo.TbContent;
import com.e3mall.utils.E3Result;

@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping(value="/content/save", method=RequestMethod.POST)
	@ResponseBody
	public E3Result addContent(TbContent content){
		return contentService.addContent(content);
	}
	
	@RequestMapping(value="/content/query/list", method=RequestMethod.GET)
	@ResponseBody
	public DataGridResult getContentList(Long categoryId, Integer page, Integer rows){
		return contentService.getContentList(categoryId, page, rows);
	}
}
