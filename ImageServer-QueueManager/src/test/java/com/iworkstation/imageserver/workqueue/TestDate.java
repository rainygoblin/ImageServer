package com.iworkstation.imageserver.workqueue;

import java.util.Date;

import org.junit.Test;

public class TestDate {

	@Test
	public void testDate() {
		Date now = new Date();
		int second = 15;
		Date nn = new Date(now.getTime() + second * 1000);
		System.out.println(now);
		System.out.println(nn);
	}
}
