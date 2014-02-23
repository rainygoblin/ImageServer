package com.iworkstation.imageserver.test;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from "/applicationContext.xml" and
// "/applicationContext-test.xml"
// in the root of the classpath
@ContextConfiguration( { "classpath:/spring/*-config.xml" })
public class SpringUnitTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	public ApplicationContext ctx;

	public SpringUnitTest() {
		super();
	}

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testInitialApplicationContext() {
		assertNotNull(ctx);
	}

	@After
	public void tearDown() throws Exception {
	}

}