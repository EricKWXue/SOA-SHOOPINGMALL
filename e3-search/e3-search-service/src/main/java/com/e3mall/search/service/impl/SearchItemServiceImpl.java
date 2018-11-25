package com.e3mall.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.easyui.SearchItem;
import com.e3mall.search.mapper.ItemMapper;
import com.e3mall.search.service.SearchItemService;
import com.e3mall.utils.E3Result;
@Service
public class SearchItemServiceImpl implements SearchItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public E3Result importAllItems() {
		try {
			List<SearchItem> searchItemList = itemMapper.getAllSearchItem();
			for (SearchItem searchItem : searchItemList) {
				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", searchItem.getId());
				doc.addField("item_title", searchItem.getTitle());
				doc.addField("item_sell_point", searchItem.getSell_point());
				doc.addField("item_price", searchItem.getPrice());
				doc.addField("item_image", searchItem.getImage());
				doc.addField("item_category_name", searchItem.getCategory_name());
				solrServer.add(doc);
			}
			solrServer.commit();
			return E3Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return E3Result.build(500, "数据导入时发生异常");
		}
		
	}

}
