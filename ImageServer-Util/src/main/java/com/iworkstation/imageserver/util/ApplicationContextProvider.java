package com.iworkstation.imageserver.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware{
	 private static ApplicationContext context;  
	  
	    public void setApplicationContext(ApplicationContext context) {  
	    	ApplicationContextProvider.context =context;  
	    }     
	    public static ApplicationContext getApplicationContext() {  
	        return context;  
	    }  
	   
	    public static <T> T getBean(Class<T> classz) {  
	        return (T) ApplicationContextProvider.context.getBean(classz);  
	    }  
	    
	    public static <T> T getBean(String beanName) {  
	        return (T) ApplicationContextProvider.context.getBean(beanName);  
	    }  
}
