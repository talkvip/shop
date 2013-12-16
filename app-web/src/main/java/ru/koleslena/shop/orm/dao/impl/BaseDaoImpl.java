package ru.koleslena.shop.orm.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.koleslena.shop.orm.dao.BaseDao;

/**
 * @author koleslena
 *
 */
@Repository
public class BaseDaoImpl implements BaseDao {

	private static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
    /**
     * Вспомогательный метод для получения текущей сессии
     *
     * @return
     */
    protected Session session() {
        return sessionFactory.getCurrentSession();
    }
	
	@Override
	public <T> T findById(Class<T> clazz, Serializable id) {
		return (T) session().get(clazz, id);
	}
	
	@Override
	public <T> List<T> findAll(String entityName) {
		return session().createQuery("from " + entityName).list();
	}

	@Override
	public <T> List<T> findPage(String entityName, int first, int count) {
		logger.info("findPage {}, {}, {} ", entityName, first, count);
		return session().createQuery("from " + entityName).setFirstResult(first).setMaxResults(count).list();
	}
	
	@Override
	public Long count(String entityName) {
		logger.info("get count {}", entityName);
		return (Long) session().createQuery("select count(*) from " + entityName).uniqueResult();
	}
	
	@Override
	@Transactional
	public void persist(Object entity) {
		logger.info("persist {}", entity.toString());
		session().persist(entity);
	}

	@Override
	@Transactional
	public void merge(Object entity) {
		logger.info("merge {}", entity.toString());
		session().merge(entity);
	}

	@Override
	@Transactional
	public void delete(Object entity) {
		logger.info("delete {}", entity.toString());
		session().delete(entity);
	}

	@Override
	public <T> void deleteById(Class<T> clazz, Serializable id) {
		logger.info("delete by id {}", id.toString());
		delete(session().get(clazz, id));
	}
}
