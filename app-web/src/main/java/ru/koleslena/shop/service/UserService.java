package ru.koleslena.shop.service;

import ru.koleslena.shop.exception.ShopException;
import ru.koleslena.shop.orm.dto.User;

/**
 * @author koleslena
 *
 */
public interface UserService {

	public User authorizeUser(String login, String password);

	public void createUser(String login, String password) throws ShopException;
	
	public User get(Long id);

}
