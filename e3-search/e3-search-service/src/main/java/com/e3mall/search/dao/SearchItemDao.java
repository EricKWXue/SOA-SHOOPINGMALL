package com.e3mall.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.e3mall.easyui.SearchItem;
import com.e3mall.easyui.SearchResult;
import com.e3mall.search.mapper.ItemMapper;
import com.e3mall.utils.E3Result;

@Repository
public class SearchItemDao {
	@Autowired
	private SolrServer solrServer;
	@Autowired
	private ItemMapper itemMapper;
	
	public SearchResult querySearchItem(SolrQuery query) throws Exception{
		//创建一个返回结果对象
		SearchResult result = new SearchResult();
		//根据查询条件查询索引库
		QueryResponse queryResponse = solrServer.query(query);
		//高亮数据
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		//取查询结果总记录数
		SolrDocumentList resultsList = queryResponse.getResults();
		long totalCount = resultsList.getNumFound();
		result.setRecordCount(totalCount);
		//创建一个商品列表对象
		List<SearchItem> itemList = new ArrayList<>();
		SearchItem item = null;
		for (SolrDocument solrDocument : resultsList) {
			item = new SearchItem();
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			item.setId((String) solrDocument.get("id"));
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			List<String> titleList = highlighting.get((String) solrDocument.get("id")).get("item_title");
			String itemTitle = "";
			if (titleList != null && titleList.size() > 0) {
				itemTitle = titleList.get(0);
			} else {
				itemTitle = (String) solrDocument.get("item_title");
			}
			item.setTitle(itemTitle);
			itemList.add(item);
		}
		result.setItemList(itemList);
		return result;
	}
	
	public E3Result addDocument(long itemId) throws Exception {
		// 1、根据商品id查询商品信息。
		SearchItem searchItem = itemMapper.getItemById(itemId);
		// 2、创建一SolrInputDocument对象。
		SolrInputDocument document = new SolrInputDocument();
		// 3、使用SolrServer对象写入索引库。
		document.addField("id", searchItem.getId());
		document.addField("item_title", searchItem.getTitle());
		document.addField("item_sell_point", searchItem.getSell_point());
		document.addField("item_price", searchItem.getPrice());
		document.addField("item_image", searchItem.getImage());
		document.addField("item_category_name", searchItem.getCategory_name());
		// 5、向索引库中添加文档。
		solrServer.add(document);
		solrServer.commit();
		// 4、返回成功，返回e3Result。
		return E3Result.ok();
	}

}
