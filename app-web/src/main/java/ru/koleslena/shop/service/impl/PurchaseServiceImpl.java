package ru.koleslena.shop.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.koleslena.shop.exception.ShopException;
import ru.koleslena.shop.orm.dao.BaseDao;
import ru.koleslena.shop.orm.dto.Goods;
import ru.koleslena.shop.orm.dto.Purchase;
import ru.koleslena.shop.orm.dto.User;
import ru.koleslena.shop.service.PurchaseService;

/**
 * @author koleslena
 *
 */
@Service
public class PurchaseServiceImpl implements PurchaseService {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseServiceImpl.class);
	
	@Inject
	private BaseDao baseDao;
	
	@Override
	public void createPurchase(Long userId, Long goodsId, BigInteger number) throws ShopException {
    	if(isGoodsEnough(goodsId, number)) {
			try {
				synchronized (this) {
			        logger.debug("trying to process {}, {}", goodsId, userId);
					processPurchase(userId, goodsId, number);
			        logger.debug("processed {}, {} purchase", goodsId, userId);
				}
			 } catch (Exception ex) {
                logger.error(ex.getMessage());
                throw new ShopException(ex);
            }
    	} else
    		throw new ShopException("There is not enough goods");
	}
	
	@Transactional(rollbackFor=Exception.class)
	private void processPurchase(Long userId, Long goodsId, BigInteger number) throws ShopException {
		logger.debug("synchronized {}", userId);
		if(isGoodsEnough(goodsId, number)) {
			Goods goods = baseDao.findById(Goods.class, goodsId);
			BigInteger count = goods.getCount();
			goods.setCount(count.subtract(number));
			
			baseDao.merge(goods);
			User user = baseDao.findById(User.class, userId);
			Purchase purchase = new Purchase();
			
			purchase.setGoods(goods);
			purchase.setUser(user);
			purchase.setPurchaseDate(new Date());
			purchase.setPurchasePrice(goods.getPrice().multiply(new BigDecimal(number)));
			
			baseDao.persist(purchase);
		} else
			throw new ShopException("There is not enough goods");
	}

	@Transactional(readOnly=true)
	private boolean isGoodsEnough(Long id, BigInteger number) {
		BigInteger count = baseDao.findById(Goods.class, id).getCount();
		BigInteger diff = count.subtract(number);
		return diff.compareTo(BigInteger.ZERO) >= 0;
	}
}
