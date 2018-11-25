package com.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.content.service.ContentCatService;
import com.e3mall.easyui.TreeNode;
import com.e3mall.mapper.TbContentCategoryMapper;
import com.e3mall.pojo.TbContentCategory;
import com.e3mall.pojo.TbContentCategoryExample;
import com.e3mall.pojo.TbContentCategoryExample.Criteria;
import com.e3mall.utils.E3Result;

@Service
public class ContentCatServiceImpl implements ContentCatService {
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<TreeNode> getContentCatById(Long parentId) {
		//设置条件
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//查询
		List<TbContentCategory> contentCategoryList = contentCategoryMapper.selectByExample(example);
		
		List<TreeNode> TreeNodeList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : contentCategoryList) {
			TreeNode node = new TreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			TreeNodeList.add(node);
		}
		return TreeNodeList;
	}

	@Override
	public E3Result addContentCat(Long parentId, String name) {
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		//1(正常)，0(删除)
		contentCategory.setStatus(1);
		//排序，默认1
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		contentCategoryMapper.insert(contentCategory);
		
		//判断父节点的isparent属性。如果不是true改为true
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!category.getIsParent()){
			category.setIsParent(true);
		}
		contentCategoryMapper.updateByPrimaryKey(category);
		return E3Result.ok(contentCategory);
	}

}
