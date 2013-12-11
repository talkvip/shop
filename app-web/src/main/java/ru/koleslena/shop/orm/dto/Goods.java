package ru.koleslena.shop.orm.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SP_GOODS", schema = "shop")
public class Goods extends AbstractGenericEntity {

	@Column(name = "GOODS_NAME")
	private String name;
	
	@Column(name = "GOODS_DESCR")
	private String descr;
	
	@Column(name = "GOODS_PRICE")
	private Double price;
	
	@Column(name = "GOODS_COUNT")
	private Long count;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("Goods: ").append("id=").append(getId())
										.append("name=").append(getName())
										.append("descr=").append(getDescr())
										.append("price=").append(getPrice())
										.append("count=").append(getCount()).toString();
	}
}
