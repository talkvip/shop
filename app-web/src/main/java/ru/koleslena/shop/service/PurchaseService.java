package ru.koleslena.shop.service;

import ru.koleslena.shop.exception.ShopException;
import ru.koleslena.shop.orm.dto.Goods;
import ru.koleslena.shop.orm.dto.User;

public interface PurchaseService {

	public void createPurchase(User user, Goods goods) throws ShopException;
}
