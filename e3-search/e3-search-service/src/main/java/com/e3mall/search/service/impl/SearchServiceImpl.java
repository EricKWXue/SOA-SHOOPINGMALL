package com.e3mall.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.e3mall.easyui.SearchResult;
import com.e3mall.search.dao.SearchItemDao;
import com.e3mall.search.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private SearchItemDao searchItemDao;
	
	@Override
	public SearchResult findSearchItem(String keyword, Integer pageNo, Integer pageSize) throws Exception {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(keyword);
		solrQuery.setStart(pageNo);
		solrQuery.setRows(pageSize);
		//设置默认搜索域
		solrQuery.set("df", "item_title");
		//开启高亮显示
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
		solrQuery.setHighlightSimplePost("</em>");
		//查询
		SearchResult searchResult = searchItemDao.querySearchItem(solrQuery);
		//设置总页码
		long recordCount = searchResult.getRecordCount();
		int totalPages = (int) ((recordCount+pageSize-1)/pageSize);
		searchResult.setTotalPages(totalPages);
		
		return searchResult;
	}

}
