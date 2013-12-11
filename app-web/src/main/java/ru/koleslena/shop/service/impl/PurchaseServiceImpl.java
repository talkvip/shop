package ru.koleslena.shop.service.impl;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.koleslena.shop.exception.ShopException;
import ru.koleslena.shop.orm.dto.Goods;
import ru.koleslena.shop.orm.dto.User;
import ru.koleslena.shop.service.PurchaseService;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	private final Logger logger = LoggerFactory.getLogger(PurchaseServiceImpl.class); 
	@SpringBean
	private SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public void createPurchase(User user, Goods goods) throws ShopException {
        Session currentSession = sessionFactory.openSession();
        
        try {
			Transaction transaction = currentSession.beginTransaction();
	        logger.info("trying to process {}", 1);
	        //processPurchase();
	        logger.info("processed {} purchase", 1);
	        transaction.commit();
        
		} finally {
	        currentSession.flush();
	        currentSession.close();
	    }
	}
}
