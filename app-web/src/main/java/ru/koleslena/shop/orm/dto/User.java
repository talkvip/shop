package ru.koleslena.shop.orm.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SP_USER", schema = "shop")
public class User extends AbstractGenericEntity {

	@Column(name = "USER_NAME")
	private String name;
	
	@Column(name = "USER_PASSWORD")
	private String password;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ROLE")
	private Role role;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("User: ").append("id=").append(getId())
										.append("name=").append(getName())
										.append("role=").append(getRole().getSpringName()).toString();
	}
}
