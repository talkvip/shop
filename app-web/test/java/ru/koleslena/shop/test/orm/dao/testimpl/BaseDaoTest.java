package ru.koleslena.shop.test.orm.dao.testimpl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ru.koleslena.shop.orm.dao.BaseDao;
import ru.koleslena.shop.orm.dto.Goods;
import ru.koleslena.shop.orm.dto.Purchase;

/**
 * @author koleslena
 *  <p/>
 * Date: Dec 17, 2013
 */
@Repository
public class BaseDaoTest implements BaseDao, Serializable {
	private static final Logger logger = LoggerFactory.getLogger(BaseDaoTest.class);
	
	private DataHolder holder = DataHolder.getInstance();
	
	@Override
	public <T> T findById(Class<T> clazz, Serializable id) {
		return (T) holder.get(clazz, id);
	}
	
	@Override
	public <T> List<T> findPage(String entityName, int first, int count) {
		if(entityName.equals(Goods.class.getSimpleName())) {
			return (List<T>) holder.findGoodsPage(first, count);
		} else if(entityName.equals(Purchase.class.getSimpleName())) {
			return (List<T>) holder.findPurchasePage(first, count);
		}
		return null;
	}
	@Override
	public Long count(String entityName) {
		return holder.count(entityName);
	}
	@Override
	public <T> List<T> findAll(String entityName) {
		return (List<T>) holder.getGoods();
	}
	@Override
	public void persist(Object entity) {
		logger.info("persist {}", entity.toString());
		holder.save(entity);
	}
	@Override
	public void merge(Object entity) {
		logger.info("merge {}", entity.toString());
		holder.merge(entity);
	}
	@Override
	public void delete(Object entity) {
		logger.info("delete {}", entity.toString());
		holder.remove(entity);
	}
	@Override
	public <T> void deleteById(Class<T> clazz, Serializable id) {
		logger.info("delete by id {}", id.toString());
	}
};
	