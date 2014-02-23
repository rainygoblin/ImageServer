package com.iworkstation.imageserver.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iworkstation.imageserver.domain.Series;
import com.iworkstation.imageserver.repository.IGenericRepository;
import com.iworkstation.imageserver.service.ISeriesManager;

@Service
public class SeriesManagerImpl implements ISeriesManager {

	@Autowired
	private IGenericRepository<String, Series> seriesRepository;

	@Override
	public List<Series> findSeriesByCriterions(
			List<Criterion> seriesCriterions,
			Map<String, List<Criterion>> seriesAssocianCriterions) {
		return seriesRepository.findEntityByCriterions(
				seriesCriterions, seriesAssocianCriterions);
	}

}
