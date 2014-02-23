package com.iworkstation.imageserver.dcm.findscp;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.net.DimseRSP;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

abstract class AbstractQuery {

	protected Criterion createRangeCondition(String propertyName, String val) {
		if (val == null)
			return null;
		if (val.contains("-")) {
			String[] vals = val.split("-");
			if (val.indexOf('-') == 0)
				return Restrictions.le(propertyName, vals[1]);
			else if (val.indexOf('-') == val.length() - 1)
				return Restrictions.ge(propertyName, vals[0]);
			else
				return Restrictions.between(propertyName, vals[0], vals[1]);
		} else
			return Restrictions.eq(propertyName, val);
	}

	protected Criterion createStringCondition(String propertyName, String val) {
		if (val == null)
			return null;
		if (val.contains("*") || val.contains("?")) {
			// keep the % for orignal char.
			String value = val.replace("%", "[%]").replace("_", "[_]");
			value = value.replace('*', '%');
			value = value.replace('?', '_');
			return Restrictions.like(propertyName, value);
		} else
			return Restrictions.eq(propertyName, val);
	}

	abstract public DimseRSP query(DicomObject data, DicomObject rsp);
}
