package ru.koleslena.shop.orm.dao;

import ru.koleslena.shop.exception.ShopException;
import ru.koleslena.shop.orm.dto.Role;
import ru.koleslena.shop.orm.dto.User;

/**
 * @author koleslena
 *
 */
public interface UserRoleDao {

	public User authUser(String login, String security);

	public User createUser(String login, String security) throws ShopException;
	
	public Role getRoleBySpringName(String name);

}
