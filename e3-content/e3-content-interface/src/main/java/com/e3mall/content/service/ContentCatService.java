package com.e3mall.content.service;

import java.util.List;

import com.e3mall.easyui.TreeNode;
import com.e3mall.utils.E3Result;

public interface ContentCatService {
	
	List<TreeNode> getContentCatById(Long parentId);
	
	E3Result addContentCat(Long parentId, String name);
}
