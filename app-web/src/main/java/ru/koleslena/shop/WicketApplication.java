package ru.koleslena.shop;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.core.util.file.WebApplicationPath;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import ru.koleslena.shop.web.GoodsListPage;
import ru.koleslena.shop.web.GoodsPage;
import ru.koleslena.shop.web.LoginPage;
import ru.koleslena.shop.web.PurchaseListPage;
import ru.koleslena.shop.web.RegistrationPage;
import ru.koleslena.shop.web.security.SpringWicketWebSession;

/**
 * @author koleslena
 *
 */
public class WicketApplication extends AuthenticatedWebApplication {

	@Override
	public Class<GoodsListPage> getHomePage() {
		return GoodsListPage.class;
	}
	
	@Override
	protected void init() {
		super.init();
		
		getResourceSettings().getResourceFinders().add(
				new WebApplicationPath(getServletContext(), "WEB-INF/views"));
		
		mountPages();
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
	}
	
	public void mountPages() {
		mountPage("login", LoginPage.class);
		mountPage("purchaselist", PurchaseListPage.class);
		mountPage("goodslist", GoodsListPage.class);
		mountPage("registration", RegistrationPage.class);
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
