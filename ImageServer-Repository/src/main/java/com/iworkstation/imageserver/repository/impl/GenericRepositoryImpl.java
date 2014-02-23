package com.iworkstation.imageserver.repository.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iworkstation.imageserver.repository.IGenericRepository;

@Repository("genericRepository")
public class GenericRepositoryImpl<Key, Entity> implements
		IGenericRepository<Key, Entity> {
	protected static final Log LOG = LogFactory
			.getLog(GenericRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("rawtypes")
	private Class entityClass;

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return entityClass;
	}

	@SuppressWarnings("rawtypes")
	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entity findById(Key id) {
		return (Entity) getCurrentSession().get(entityClass, (Serializable) id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entity findLockedEntityById(Key id) {
		return (Entity) getCurrentSession().get(entityClass, (Serializable) id,
				LockOptions.UPGRADE);

	}

	@Override
	public void saveOrUpdateEntity(Entity entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	@Override
	public void flush() {
		getCurrentSession().flush();
	}

	@Override
	public void delete(Entity entity) {
		getCurrentSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Entity> loadAll() {
		Criteria criteria = getCurrentSession().createCriteria(entityClass);
		return criteria.list();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void lock(Entity entity) {
		getCurrentSession().lock(entity, LockMode.UPGRADE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Entity> findByProp(String paramString, Object paramObject) {
		return getCurrentSession().createCriteria(this.entityClass)
				.add(Restrictions.eq(paramString, paramObject))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Entity> findByProps(Map<String, Object> props) {
		Criteria criteria = getCurrentSession()
				.createCriteria(this.entityClass).setResultTransformer(
						Criteria.DISTINCT_ROOT_ENTITY);

		for (String prop : props.keySet()) {
			criteria.add(Restrictions.eq(prop, props.get(prop)));
		}
		return criteria.list();
	}

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entity findUniqueByProp(String paramString, Object paramObject) {
		return (Entity) getCurrentSession().createCriteria(this.entityClass)
				.add(Restrictions.eq(paramString, paramObject))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entity findUniqueByProps(Map<String, Object> props) {
		Criteria criteria = getCurrentSession()
				.createCriteria(this.entityClass).setResultTransformer(
						Criteria.DISTINCT_ROOT_ENTITY);

		for (String prop : props.keySet()) {
			criteria.add(Restrictions.eq(prop, props.get(prop)));
		}
		return (Entity) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Entity> findEntityByCriterions(List<Criterion> criterions,
			Map<String, List<Criterion>> associationCriterions) {
		Criteria criteria = getCurrentSession()
				.createCriteria(this.entityClass);
		if (criterions != null) {
			for (Criterion criterion : criterions) {
				criteria.add(criterion);
			}
		}
		if (associationCriterions != null) {
			for (String associationPath : associationCriterions.keySet()) {
				Criteria associationCriteria = criteria
						.createCriteria(associationPath);
				for (Criterion criterion : associationCriterions
						.get(associationPath)) {
					associationCriteria.add(criterion);
				}
			}
		}
		return criteria.list();
	}
}
