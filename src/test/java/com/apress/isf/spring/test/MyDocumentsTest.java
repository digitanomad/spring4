package com.apress.isf.spring.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apress.isf.java.model.Document;
import com.apress.isf.java.model.Type;
import com.apress.isf.java.service.SearchEngine;
import com.apress.isf.spring.amqp.RabbitMQProducer;
import com.apress.isf.spring.data.DocumentRepository;
import com.apress.isf.spring.jms.JMSProducer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/spring/mydocuments-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyDocumentsTest {

	private static final Logger log = LoggerFactory.getLogger(MyDocumentsTest.class);
	// META-INF/data/jms.txt 기반 - 오직 한 개 레코드
	private static final int MAX_ALL_DOCS = 5;
	private static final int MAX_WEB_DOCS = 2;
	private static final String DOCUMENT_ID = "df569fa4-a513-4252-9810-818cade184ca";
	
	@Autowired
    private SearchEngine engine;
	
	@Autowired
	JMSProducer jmsProducer;
    
	@Test
	public void testSpringJMS_1() {
		log.debug("Testing Spring JMS producer...");
		jmsProducer.send();
	}
	
    @Test
    public void testSpringJMS_2() throws InterruptedException {
    	log.debug("Testing Spring JMS Listener/Insert...");
    	assertNotNull(engine);
    	
    	// 메시지가 소비되도록 최소 5초간 대기한다.
    	Thread.sleep(5000);
    	// JMS 메시지 수신 및 삽입 후 문서가 반드시 5개여야 한다.
    	assertEquals(MAX_ALL_DOCS, engine.listAll().size());
    	
    	Type documentType = new Type("WEB", ".url");
    	assertEquals(MAX_WEB_DOCS, engine.findByType(documentType).size());
    }
    
    @Autowired
    RabbitMQProducer rabbitmqProducer;
    @Autowired
    DocumentRepository repository;
    
    @Test
    public void testSpringRabbitMQ_1() {
    	log.debug("Testing RabbitMQ producer...");
    	assertNotNull(rabbitmqProducer);
    	
    	Document document = repository.findById(DOCUMENT_ID);
    	rabbitmqProducer.send(document);
    }
    
}
