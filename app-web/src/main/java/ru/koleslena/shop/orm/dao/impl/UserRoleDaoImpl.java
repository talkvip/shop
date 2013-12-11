package ru.koleslena.shop.orm.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.stereotype.Repository;

import ru.koleslena.shop.exception.ShopException;
import ru.koleslena.shop.orm.dao.BaseDao;
import ru.koleslena.shop.orm.dao.UserRoleDao;
import ru.koleslena.shop.orm.dto.Role;
import ru.koleslena.shop.orm.dto.User;

@Repository
public class UserRoleDaoImpl implements UserRoleDao {
	
	public final String STRING_USER_ROLE_NAME = "user";
	
	@SpringBean
	private BaseDao baseDao;
	
	@PersistenceContext 
	private EntityManager em;
	
	@Override
	public User authUser(String login, String security) {
		return (User) em.createQuery("FROM User as u WHERE u.name like :name AND u.password like :pass ")
				.setParameter("name", login)
				.setParameter("pass", security).getSingleResult();
	}
	
	@Override
	public User createUser(String login, String security) throws ShopException {
		
		Role userRole = getRoleBySpringName(STRING_USER_ROLE_NAME);
		
		if(userRole == null)
			throw new ShopException("Не правильно настроены роли. Не найдена роль user.");
		
		Integer roleId = userRole.getId();
		
		User user = new User();
		user.setName(login);
		user.setPassword(security);
		user.setRole((Role)baseDao.findById(Role.class, roleId));
		
		baseDao.persist(user);
		
		return user;
	}
	
	public Role getRoleBySpringName(String name) {
		return (Role) em.createQuery("FROM Role as r WHERE r.springName like :name")
				.setParameter("name", name).getSingleResult();
	}
}
