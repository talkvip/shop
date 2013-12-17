package ru.koleslena.shop.service;

import java.math.BigInteger;

import ru.koleslena.shop.exception.ShopException;

/**
 * @author koleslena
 *
 */
public interface PurchaseService {

	public void createPurchase(Long userId, Long goodsId, BigInteger number) throws ShopException;
}
