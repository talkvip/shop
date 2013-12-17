package ru.koleslena.shop.orm.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author koleslena
 *
 */
@Entity
@Table(name = "SP_GOODS", schema = "shop")
public class Goods extends AbstractGenericEntity {

	private String name;

	private String descr;

	private BigDecimal price;

	private BigInteger count;

	private Long id;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GOODS_ID", nullable = false)
	public Long getId() {
	    return id;
	}
	
	public void setId(Long id) {
	    this.id = id;
	}
	
	@Column(name = "GOODS_NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "GOODS_DESCR")
	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	@Column(name = "GOODS_PRICE")
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@Column(name = "GOODS_COUNT")
	public BigInteger getCount() {
		return count;
	}

	public void setCount(BigInteger count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("Goods:").append(" id=").append(getId())
										.append(", name=").append(getName())
										.append(", descr=").append(getDescr())
										.append(", price=").append(getPrice())
										.append(", count=").append(getCount()).toString();
	}
}
