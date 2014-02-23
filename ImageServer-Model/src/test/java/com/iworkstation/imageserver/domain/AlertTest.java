package com.iworkstation.imageserver.domain;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import com.iworkstation.imageserver.enumeration.AlertCategoryEnum;
import com.iworkstation.imageserver.enumeration.AlertLevelEnum;

public class AlertTest {

	@Ignore
	@Test
	public void testGetAlert() {
		Alert alert = new Alert();
		alert.setInsertTime(new Date());
		alert.setComponent("te");
		alert.setSource("as");
		alert.setTypeCode(10);
		alert.setAlertCategoryEnum(AlertCategoryEnum.Application);
		alert.setAlertLevelEnum(AlertLevelEnum.Error);
		alert.setContent("<test></test>");
		System.out.println("count:" + alert);
	}

}
