package ru.koleslena.shop.web.security;

import java.io.Serializable;

/**
 * @author koleslena
 *
 */
public class UserDetails implements Serializable {

    private final Long id;

    private final String name;


    public UserDetails(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
    	return new StringBuilder("User: ").append("id=").append(getId())
				.append("name=").append(getName()).toString();
    }
}
