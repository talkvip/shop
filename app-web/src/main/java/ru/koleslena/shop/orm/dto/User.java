package ru.koleslena.shop.orm.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author koleslena
 *
 */
@Entity
@Table(name = "SP_USER", schema = "shop")
public class User extends AbstractGenericEntity {

	private String name;

	private String password;

	private Role role;
	
	private Long id;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID", nullable = false)
	public Long getId() {
	    return id;
	}
	
	public void setId(Long id) {
	    this.id = id;
	}

	@Column(name = "USER_NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "USER_PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ROLE")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("User:").append(" id=").append(getId())
										.append(", name=").append(getName())
										.append(", role=").append(getRole().getSpringName()).toString();
	}
}
