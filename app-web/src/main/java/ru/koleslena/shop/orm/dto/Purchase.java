package ru.koleslena.shop.orm.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SP_PURCHASE", schema = "shop")
public class Purchase extends AbstractGenericEntity {

	private User user;

	private Goods goods;

	private Date purchaseDate;

	private Double purchasePrice;

	private Long id;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PURCHASE_ID", nullable = false)
	public Long getId() {
	    return id;
	}
	
	public void setId(Long id) {
	    this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PURCHASE_USER")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PURCHASE_GOODS")
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	
	@Column(name = "PURCHASE_DATE")
	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	@Column(name = "PURCHASE_PRICE")
	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("Purchase:").append(" id=").append(getId())
										.append(", user=").append(getUser().getId()).append(":").append(getUser().getName())
										.append(", goods=").append(getGoods().getId()).append(":").append(getGoods().getName())
										.append(", price=").append(getPurchasePrice())
										.append(", date=").append(getPurchaseDate()).toString();
	}
}
