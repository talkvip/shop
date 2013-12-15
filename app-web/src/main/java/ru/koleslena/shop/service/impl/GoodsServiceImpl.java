package ru.koleslena.shop.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ru.koleslena.shop.orm.dao.BaseDao;
import ru.koleslena.shop.orm.dto.Goods;
import ru.koleslena.shop.service.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService {
	
	@Inject
	private BaseDao baseDao;
	
	@Override
	public void createGoods(Goods goods) {
		baseDao.persist(goods);
	}
	
	@Override
	public void updateGoods(Goods goods) {
		baseDao.merge(goods);
	}
	
	@Override
	public void deleteGoods(Goods goods) {
		baseDao.delete(goods);
	}
	
	@Override
	public List<Goods> getAllGoods() {
		return baseDao.findAll(Goods.class.getSimpleName());
	}

	@Override
	public Goods get(Long id) {
		return baseDao.findById(Goods.class, id);
	}
}
