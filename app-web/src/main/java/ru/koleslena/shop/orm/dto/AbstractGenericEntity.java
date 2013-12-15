package ru.koleslena.shop.orm.dto;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractGenericEntity implements Serializable {

	private static final String ID = "id";
	
}
