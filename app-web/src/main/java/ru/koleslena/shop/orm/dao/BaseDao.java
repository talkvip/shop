package ru.koleslena.shop.orm.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao {

	public <T> T findById(Class<T> clazz, Serializable id);

	public <T> List<T> findAll(String entityName);

	public void persist(Object entity);

	public void merge(Object entity);

	public void delete(Object entity);

	public <T> void deleteById(Class<T> clazz, Serializable id);

}
