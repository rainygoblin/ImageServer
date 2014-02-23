package com.iworkstation.imageserver.repository;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

public interface IGenericRepository<Key, Entity> {
	// / <summary>
	// / 获取实体
	// / </summary>
	// / <param name="id">主键</param>
	// / <returns></returns>
	Entity findById(Key id);

	// / <summary>
	// / 获取实体
	// / </summary>
	// / <param name="id">主键</param>
	// / <returns></returns>
	Entity findLockedEntityById(Key id);

	// / <summary>
	// / 插入实体
	// / </summary>
	// / <param name="entity">实体</param>
	// / <returns></returns>
	void saveOrUpdateEntity(Entity entity);

	// / <summary>
	// / 修改实体
	// / </summary>
	// / <param name="entity">实体</param>
	// / <returns></returns>
	void flush();

	// / <summary>
	// / 删除实体
	// / </summary>
	// / <param name="entity">实体</param>
	// / <returns></returns>
	void delete(Entity entity);

	// / <summary>
	// / 获取全部集合
	// / </summary>
	// / <returns></returns>
	List<Entity> loadAll();

	void lock(Entity entity);

	List<Entity> findByProp(String paramString, Object paramObject);

	Entity findUniqueByProp(String paramString, Object paramObject);

	List<Entity> findByProps(Map<String, Object> props);

	Entity findUniqueByProps(Map<String, Object> props);

	List<Entity> findEntityByCriterions(List<Criterion> criterions,
			Map<String, List<Criterion>> associationCriterions);

}
