package com.e3mall.activemq;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ActiveMqSpring {

	@Test
	public void MegCustomer() throws IOException {
		ApplicationContext application = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-activemq.xml");
		// 等待
		System.in.read();
	}
}
