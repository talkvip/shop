package ru.koleslena.shop;

import org.apache.wicket.injection.Injector;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseInjectBean {
	
	public BaseInjectBean() {
		injectDependencies();
	}
	
    protected void injectDependencies() {
        Injector.get().inject(this);
    }
}
