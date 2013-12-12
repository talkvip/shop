package ru.koleslena.shop.orm.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SP_ROLE", schema = "shop")
public class Role {
	
	public final static String STRING_USER_ROLE_NAME = "user";
	public final static String STRING_ADMIN_ROLE_NAME = "admin";

	@Column(name = "ROLE_ID")
	private Integer id;
	
	@Column(name = "ROLE_NAME")
	private String name;

	@Column(name = "ROLE_SPRING_NAME")
	private String springName;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSpringName() {
		return springName;
	}
	
	public void setSpringName(String springName) {
		this.springName = springName;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("Role: ").append("id=").append(getId())
										.append("name=").append(getName())
										.append("apringName=").append(getSpringName()).toString();
	}
}
