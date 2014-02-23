package com.iworkstation.imageserver.service;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.iworkstation.imageserver.domain.Patient;

public interface IPatientManager {

	List<Patient> findPatientByCriterions(List<Criterion> criterions,
			Map<String, List<Criterion>> associationCriterions);
}
