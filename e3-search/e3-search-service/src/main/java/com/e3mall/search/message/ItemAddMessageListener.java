package com.e3mall.search.message;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.e3mall.search.dao.SearchItemDao;

public class ItemAddMessageListener implements MessageListener{
	@Autowired
	private SearchItemDao searchItemDao;
	
	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textMessage= (TextMessage)message;		
			Long itemId = Long.valueOf(textMessage.getText());
			Thread.sleep(1000);//等待1秒
			searchItemDao.addDocument(itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
