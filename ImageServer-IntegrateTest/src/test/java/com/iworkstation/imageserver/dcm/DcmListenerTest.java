package com.iworkstation.imageserver.dcm;

import org.junit.Test;
import org.springframework.core.task.TaskExecutor;

import com.iworkstation.imageserver.test.SpringUnitTest;
import com.iworkstation.imageserver.util.ApplicationContextProvider;


public class DcmListenerTest extends SpringUnitTest{

	@Test
	public void testDcmRcv(){
//		ApplicationContextProvider ApplicationContextProvider=ctx.getBean(ApplicationContextProvider.class);
		
//		storageSCP=ctx.getBean(StorageSCP.class);
//		storageSCP=ctx.getBean(StorageSCP.class);
//		storageSCP=ctx.getBean(StorageSCP.class);
		TaskExecutor taskExecutor=ApplicationContextProvider.getBean("taskExecutor");
		TaskExecutor taskExecutor1=ApplicationContextProvider.getBean("taskExecutor");
		TaskExecutor taskExecutor2=ApplicationContextProvider.getBean("taskExecutor");
		try {
			Thread.sleep(10000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
