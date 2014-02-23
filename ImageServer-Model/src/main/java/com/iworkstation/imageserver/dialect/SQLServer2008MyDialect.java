package com.iworkstation.imageserver.dialect;

import org.hibernate.dialect.SQLServer2008Dialect;

public class SQLServer2008MyDialect extends SQLServer2008Dialect {
	public SQLServer2008MyDialect() {
		super();
		registerHibernateType(1, "string");
		registerHibernateType(-9, "string");
		registerHibernateType(-16, "string");
		registerHibernateType(3, "double");
	}
}
