package ru.koleslena.shop.orm.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SP_PURCHASE", schema = "shop")
public class Purchase extends AbstractGenericEntity {
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PURCHASE_USER")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PURCHASE_GOODS")
	private Goods goods;
	
	@Column(name = "PURCHASE_DATE")
	private Date purchaseDate;
	
	@Column(name = "PURCHASE_PRICE")
	private Double purchasePrice;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("Purchase: ").append("id=").append(getId())
										.append("user=").append(getUser().getId()).append(":").append(getUser().getName())
										.append("goods=").append(getGoods().getId()).append(":").append(getGoods().getName())
										.append("price=").append(getPurchasePrice())
										.append("date=").append(getPurchaseDate()).toString();
	}
}
