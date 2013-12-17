package ru.koleslena.shop.orm.dao.impl;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ru.koleslena.shop.exception.ShopException;
import ru.koleslena.shop.orm.dao.BaseDao;
import ru.koleslena.shop.orm.dao.UserRoleDao;
import ru.koleslena.shop.orm.dto.Role;
import ru.koleslena.shop.orm.dto.User;

/**
 * @author koleslena
 *
 */
@Repository
public class UserRoleDaoImpl implements UserRoleDao {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	@Inject
	private BaseDao baseDao;

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
	public User authUser(String login, String security) {
		return (User) session().createQuery("FROM User as u WHERE u.name like :name AND u.password like :pass ")
				.setParameter("name", login)
				.setParameter("pass", security).uniqueResult();
	}
	
	@Override
	public void createUser(String login, String security) throws ShopException {
		
		Role userRole = getRoleBySpringName(Role.STRING_USER_ROLE_NAME);
		
		if(userRole == null)
			throw new ShopException("Не правильно настроены роли. Не найдена роль user.");
		
		User user = new User();
		user.setName(login);
		user.setPassword(security);
		user.setRole(userRole);
		
		baseDao.persist(user);
		
		return;
	}
	
	public Role getRoleBySpringName(String name) {
		return (Role) session().createQuery("FROM Role as r WHERE r.springName like :name")
				.setParameter("name", name).uniqueResult();
	}
}
