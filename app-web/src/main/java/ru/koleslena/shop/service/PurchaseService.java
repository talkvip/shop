package ru.koleslena.shop.service;

import ru.koleslena.shop.exception.ShopException;

/**
 * @author koleslena
 *
 */
public interface PurchaseService {

	public void createPurchase(Long userId, Long goodsId) throws ShopException;
}
