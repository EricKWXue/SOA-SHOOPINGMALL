package com.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.e3mall.content.service.ContentService;
import com.e3mall.easyui.DataGridResult;
import com.e3mall.jedis.JedisClient;
import com.e3mall.mapper.TbContentMapper;
import com.e3mall.pojo.TbContent;
import com.e3mall.pojo.TbContentExample;
import com.e3mall.pojo.TbContentExample.Criteria;
import com.e3mall.utils.E3Result;
import com.e3mall.utils.JsonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;
	
	@Override
	public E3Result addContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		//删除缓存（作用：同步缓存）
		try {
			jedisClient.hdel(CONTENT_LIST, String.valueOf(content.getCategoryId()));			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return E3Result.ok();
	}

	@Override
	public DataGridResult getContentList(Long categoryId, int pageNo, int pageSize) {
		//分页
		PageHelper.startPage(pageNo, pageSize);
		//查询
		TbContentExample contentExample = new TbContentExample();
		Criteria criteria = contentExample.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> contentList = contentMapper.selectByExample(contentExample);
		//获取查询后的分页信息
		PageInfo<TbContent> pageInfo = new PageInfo<>(contentList);
		//存储结果集
		DataGridResult result = new DataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(contentList);
		//返回
		return result;
	}

	@Override
	public List<TbContent> getContentListByCatId(Long categoryId) {
		//先查redis
		try {
			String json = jedisClient.hget(CONTENT_LIST, String.valueOf(categoryId));
			if(StringUtils.isNotBlank(json)){
				List<TbContent> contentList = JsonUtils.jsonToList(json, TbContent.class);
				return contentList;			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//再查数据库
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		
		List<TbContent> tbContentList = contentMapper.selectByExampleWithBLOBs(example);
		//存入缓存
		try {
			jedisClient.hset(CONTENT_LIST, String.valueOf(categoryId), JsonUtils.objectToJson(tbContentList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbContentList;
	}

}
