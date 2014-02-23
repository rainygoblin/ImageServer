package com.iworkstation.imageserver.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iworkstation.imageserver.domain.Patient;
import com.iworkstation.imageserver.repository.IGenericRepository;
import com.iworkstation.imageserver.service.IPatientManager;

@Service
public class PatientManagerImpl implements IPatientManager {

	@Autowired
	private IGenericRepository<String, Patient> patientRepository;

	@Override
	public List<Patient> findPatientByCriterions(
			List<Criterion> seriesCriterions,
			Map<String, List<Criterion>> seriesAssocianCriterions) {
		List<Patient> foundPatients = patientRepository.findEntityByCriterions(
				seriesCriterions, seriesAssocianCriterions);

		return foundPatients;
	}

}
