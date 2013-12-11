package ru.koleslena.shop.service;

import ru.koleslena.shop.exception.ShopException;
import ru.koleslena.shop.orm.dto.User;

public interface UserService {

	public User authorizeUser(String login, String password);

	public User createUser(String login, String password) throws ShopException;

}
