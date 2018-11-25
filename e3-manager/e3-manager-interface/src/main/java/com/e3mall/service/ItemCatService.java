package com.e3mall.service;

import java.util.List;

import com.e3mall.easyui.TreeNode;

public interface ItemCatService {
	//获取商品树形结构
	List<TreeNode> getTreeNode(Long parentId);
}
