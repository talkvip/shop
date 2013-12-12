package ru.koleslena.shop.orm.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.koleslena.shop.orm.dao.BaseDao;

@Repository
public class BaseDaoImpl implements BaseDao {

	private final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	@PersistenceContext 
	private EntityManager em;
	
	@Override
	public <T> T findById(Class<T> clazz, Serializable id) {
		return em.find(clazz, id);
	}
	
	@Override
	public <T> List<T> findAll(String entityName) {
		return em.createQuery("from " + entityName).getResultList();
	}

	@Override
	public <T> List<T> findPage(String entityName, int first, int count) {
		return em.createQuery("from " + entityName).setFirstResult(first).setMaxResults(count).getResultList();
	}
	
	@Override
	public Long count(String entityName) {
		return (Long) em.createQuery("select count('x') from " + entityName).getSingleResult();
	}
	
	@Override
	@Transactional
	public void persist(Object entity) {
		logger.info("persist {}", entity.toString());
		em.persist(entity);
	}

	@Override
	@Transactional
	public void merge(Object entity) {
		logger.info("merge {}", entity.toString());
		em.merge(entity);
	}

	@Override
	@Transactional
	public void delete(Object entity) {
		logger.info("delete {}", entity.toString());
		em.remove(entity);
	}

	@Override
	public <T> void deleteById(Class<T> clazz, Serializable id) {
		logger.info("delete by id {}", id.toString());
		delete(em.find(clazz, id));
	}
}
