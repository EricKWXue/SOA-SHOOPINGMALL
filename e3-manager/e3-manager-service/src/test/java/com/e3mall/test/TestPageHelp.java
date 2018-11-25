package com.e3mall.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.e3mall.mapper.TbItemMapper;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@SuppressWarnings("resource")
public class TestPageHelp {
	public void PageHelp() {
		//初始化一个spring容器		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		//取一个查询对象
		TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
		//设置分页信息
		PageHelper.startPage(1, 10);
		//执行查询,查询所有
		TbItemExample example = new TbItemExample();
		List<TbItem> itemList = itemMapper.selectByExample(example);
		//获取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(itemList);
		System.out.println(pageInfo.getPages());//总页数
		System.out.println(pageInfo.getTotal());//总数
		System.out.println(itemList.size());
		
		
	}
}
