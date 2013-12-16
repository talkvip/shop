package ru.koleslena.shop.orm.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author koleslena
 *
 */
public interface BaseDao {

	public <T> T findById(Class<T> clazz, Serializable id);
	
	public <T> List<T> findPage(String entityName, int first, int count);

	public Long count(String entityName);
	
	public <T> List<T> findAll(String entityName);

	public void persist(Object entity);

	public void merge(Object entity);

	public void delete(Object entity);

	public <T> void deleteById(Class<T> clazz, Serializable id);

}
