package ru.koleslena.shop.service.impl;

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

@Service
public class PurchaseServiceImpl implements PurchaseService {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseServiceImpl.class);
	
	@Inject
	private BaseDao baseDao;
	
	@Override
	public void createPurchase(Long userId, Long goodsId) throws ShopException {
    	if(isGoodsEnough(goodsId)) {
			try {
				synchronized (this) {
			        logger.debug("trying to process {}, {}", goodsId, userId);
					processPurchase(userId, goodsId);
			        logger.debug("processed {}, {} purchase", goodsId, userId);
				}
			 } catch (Exception ex) {
                logger.error(ex.getMessage());
                throw new ShopException(ex);
            }
    	} else
    		throw new ShopException("Goods, " + goodsId + " is not enouth");
	}
	
	@Transactional(rollbackFor=Exception.class)
	private void processPurchase(Long userId, Long goodsId) throws ShopException {
		logger.debug("synchronized {}", userId);
		if(isGoodsEnough(goodsId)) {
			Goods goods = baseDao.findById(Goods.class, goodsId);
			Long count = goods.getCount();
			goods.setCount(--count);
			
			try {
                Object monitor = new Object();
                synchronized (monitor) {
                    monitor.wait(3000);
                }
            } catch (Exception ex) {
                throw new ShopException(ex);
            }
			
			baseDao.merge(goods);
			
			User user = baseDao.findById(User.class, userId);
			
			Purchase purchase = new Purchase();
			
			purchase.setGoods(goods);
			purchase.setUser(user);
			purchase.setPurchaseDate(new Date());
			purchase.setPurchasePrice(goods.getPrice());
			
			baseDao.persist(purchase);
		} else
			throw new ShopException("Goods - " + goodsId + " is not enouth");
	}

	@Transactional(readOnly=true)
	private boolean isGoodsEnough(Long id) {
		return baseDao.findById(Goods.class, id).getCount().compareTo(new Long(0)) > 0;
	}
}
