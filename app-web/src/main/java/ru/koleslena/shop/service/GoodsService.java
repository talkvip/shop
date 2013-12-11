package ru.koleslena.shop.service;

import java.util.List;

import ru.koleslena.shop.orm.dto.Goods;

public interface GoodsService {

	public void createGoods(Goods goods);
	public void updateGoods(Goods goods);
	public void deleteGoods(Goods goods);
	
	public List<Goods> getAllGoods();
	
}
