package com.iworkstation.imageserver.service;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.iworkstation.imageserver.domain.Series;

public interface ISeriesManager {

	List<Series> findSeriesByCriterions(List<Criterion> seriesCriterions,
			Map<String, List<Criterion>> seriesAssocianCriterions);

}
