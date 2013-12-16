package ru.koleslena.shop.service;

import java.util.List;

import ru.koleslena.shop.orm.dto.Goods;

/**
 * @author koleslena
 *
 */
public interface GoodsService {

	public void createGoods(Goods goods);
	public void updateGoods(Goods goods);
	public void deleteGoods(Goods goods);
	
	public List<Goods> getAllGoods();
	public Goods get(Long id);
	
}
