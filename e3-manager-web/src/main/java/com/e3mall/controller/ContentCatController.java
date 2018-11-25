package com.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.content.service.ContentCatService;
import com.e3mall.easyui.TreeNode;
import com.e3mall.utils.E3Result;

@Controller
public class ContentCatController {
	@Autowired
	private ContentCatService contentCatService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<TreeNode> getContentCatList(@RequestParam(name="id", defaultValue="0")Long parentId){		
		return contentCatService.getContentCatById(parentId);
	}
	
	@RequestMapping("/content/category/create")
	@ResponseBody
	public E3Result addContentCat(Long parentId, String name){
		return contentCatService.addContentCat(parentId, name);
	}
}
