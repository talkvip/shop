package ru.koleslena.shop;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import ru.koleslena.shop.web.GoodsListPage;
import ru.koleslena.shop.web.GoodsPage;
import ru.koleslena.shop.web.LoginPage;
import ru.koleslena.shop.web.PurchaseListPage;
import ru.koleslena.shop.web.SignInPage;
import ru.koleslena.shop.web.security.SpringWicketWebSession;

public class WicketApplication extends AuthenticatedWebApplication {

	@Override
	public Class<GoodsListPage> getHomePage() {
		return GoodsListPage.class;
	}
	
	@Override
	protected void init() {
		super.init();
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		mountPages();
	}
	
	public void mountPages() {
		mountPage("login", LoginPage.class);
		mountPage("purchaselist", PurchaseListPage.class);
		mountPage("signin", SignInPage.class);
		mountPage("goods", GoodsPage.class);
	}
	
	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return LoginPage.class;
	}
	
	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return SpringWicketWebSession.class;
	}

}
