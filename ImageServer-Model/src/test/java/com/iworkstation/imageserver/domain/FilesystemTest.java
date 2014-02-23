package com.iworkstation.imageserver.domain;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FilesystemTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFreeSpace() {
		File file = new File("c:/");
		file.getTotalSpace();
		long freeBytes = file.getFreeSpace();

		boolean reulst = freeBytes / 1024.0 / 1024.0 < 1024;
		assertFalse(reulst);
		int i = 0;
		i++;
		System.out.print(i);
	}

}
