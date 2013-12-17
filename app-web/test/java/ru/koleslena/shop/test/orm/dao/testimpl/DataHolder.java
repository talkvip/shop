package ru.koleslena.shop.test.orm.dao.testimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.koleslena.shop.orm.dto.AbstractGenericEntity;
import ru.koleslena.shop.orm.dto.Goods;
import ru.koleslena.shop.orm.dto.Purchase;
import ru.koleslena.shop.orm.dto.Role;
import ru.koleslena.shop.orm.dto.User;

/**
 * @author koleslena
 *  <p/>
 * Date: Dec 17, 2013
 */
public class DataHolder {
	private static final Logger logger = LoggerFactory.getLogger(DataHolder.class);
	
	private List<Goods> goods = new ArrayList<Goods>();
	private List<Purchase> purs = new ArrayList<Purchase>();
	private List<User> users = new ArrayList<User>();
	
	private static DataHolder instance;
	
	public static DataHolder getInstance() {
		if(instance == null) {
			synchronized (DataHolder.class) {
				instance = new DataHolder();
			}
        }
        return instance;
	}
	
	private DataHolder() {
		for(int i = 1; i < 16; i++) {
			Goods g = new Goods();
			g.setId(new Long(i));
			g.setName("name" + i);
			g.setDescr("name" + i);
			g.setCount(BigInteger.TEN);
			g.setPrice(new BigDecimal(5));
			goods.add(g);
		}
		
		User u = new User();
		u.setId(new Long(1));
		u.setName("lena");
		u.setRole(getRole(Role.STRING_USER_ROLE_NAME));
		
		users.add(u);
		
		u = new User();
		u.setId(new Long(2));
		u.setName("admin");
		u.setRole(getRole(Role.STRING_ADMIN_ROLE_NAME));
		
		users.add(u);
	}
	
	public Role getRole(String name) {
		Role r = new Role();
		r.setId(new Integer(name.length()));
		r.setSpringName(name);
		return r;
	}
	
	public <T> AbstractGenericEntity get(Class<T> clazz, Serializable id) {
		if(clazz.equals(User.class)) {
			for(Iterator<User> i = users.iterator();i.hasNext();) {
				User g = i.next();
				if(g.getId().equals(id)) {
					return g;
				}
			}
		} else if(clazz.equals(Goods.class)) {
			for(Iterator<Goods> i = goods.iterator();i.hasNext();) {
				Goods g = i.next();
				if(g.getId().equals(id))
					return g;
			}
		} else if(clazz.equals(Purchase.class)) {
			for(Iterator<Purchase> i = purs.iterator();i.hasNext();) {
				Purchase g = i.next();
				if(g.getId().equals(id))
					return g;
			}
		}
		return null;
	}
	
	public List<Goods> findGoodsPage(int first, int count) {
		return goods.subList(first, first + count);
	}
	
	public List<Purchase> findPurchasePage(int first, int count) {
		return purs.subList(first, first + count);
	}
	
	public List<Goods> getGoods() {
		return goods;
	}
	
	public Long count(String entityName) {
		if(entityName.equals(Goods.class.getSimpleName())) {
			return new Long(goods.size());
		} else if(entityName.equals(Purchase.class.getSimpleName())) {
			return new Long(purs.size());
		}
		return null;
	}
	
	public void save(Object entity) {
		if(entity.getClass().equals(Goods.class)) {
			Goods g = (Goods) entity;
			g.setId(new Long(goods.size() + 1));
			goods.add(g);
		} else if(entity.getClass().equals(User.class)) {
			User g = (User) entity;
			g.setId(new Long(users.size() + 1));
			users.add(g);
		} else if(entity.getClass().equals(Purchase.class)) {
			Purchase g = (Purchase) entity;
			g.setId(new Long(purs.size()) + 1);
			purs.add(g);
		}
	}
	
	public void merge(Object entity) {
		if(entity.getClass().equals(Goods.class)) {
			Goods gnew = (Goods) entity;
			Goods gold = (Goods) get(Goods.class, gnew.getId());
			gold.setName(gnew.getName());
			gold.setDescr(gnew.getDescr());
			gold.setPrice(gnew.getPrice());
			gold.setCount(gnew.getCount());
		} 
		/*else if(entity.getClass().equals(User.class)) {
			users.add((User) entity);
		} else if(entity.getClass().equals(Purchase.class)) {
			purs.add((Purchase) entity);
		}*/
	}
	
	public void remove(Object entity) {
		if(entity.getClass().equals(Goods.class)) {
			goods.remove(entity);
		} else if(entity.getClass().equals(User.class)) {
			users.remove(entity);
		} else if(entity.getClass().equals(Purchase.class)) {
			purs.remove(entity);
		}
	}
	
	public <T> void remove(Class<T> clazz, Serializable id) {
		remove(get(clazz, id));
	}
}
