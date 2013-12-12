package ru.koleslena.shop.web.provider;

import java.io.Serializable;
import java.util.Iterator;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.util.Assert;

import ru.koleslena.shop.orm.dao.BaseDao;

public class PagerDataProvider<T> implements IDataProvider<T> {
	
	@SpringBean
	private BaseDao baseDao;
	
	private Class objectClass;
	
	public PagerDataProvider(Class clazz) {
		Assert.notNull(clazz, "Class must be specified for DataProvider");
		objectClass = clazz;
	}

	@Override
	public void detach() {
	}

	@Override
	public Iterator<T> iterator(long first, long count) {
		if(first < 0 || first > Integer.MAX_VALUE)
			return null;
		return (Iterator<T>)baseDao.findPage(objectClass.getSimpleName(), (int)first, (int)count).iterator();
	}

	@Override
	public long size() {
		return baseDao.count(objectClass.getSimpleName());
	}

	@Override
	public IModel<T> model(T object) {
		return new Model((Serializable) object);
	};
}
