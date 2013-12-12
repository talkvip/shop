package ru.koleslena.shop.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.koleslena.shop.orm.dto.Purchase;
import ru.koleslena.shop.orm.dto.Role;
import ru.koleslena.shop.web.provider.PagerDataProvider;
import ru.koleslena.shop.web.security.SpringWicketWebSession;

public class PurchaseListPage extends BasePage {
	private final Logger logger = LoggerFactory.getLogger(GoodsListPage.class);
	
	public PurchaseListPage() {
		super();
		SpringWicketWebSession session = (SpringWicketWebSession) getSession();
		
		if(session.hasRole(Role.STRING_ADMIN_ROLE_NAME)) {
			IDataProvider<Purchase> dataProvider = new PagerDataProvider<Purchase>(Purchase.class);
			
			DataView<Purchase> dataView = new DataView<Purchase>("rows", dataProvider, 20) {
				@Override
				protected void populateItem(final Item<Purchase> item) {
					final Purchase purchase = item.getModelObject();
					item.add(new Label("name", purchase.getGoods().getName()));
					item.add(new Label("name", purchase.getUser().getName()));
					item.add(new Label("price", purchase.getPurchasePrice()));
					item.add(new Label("description", purchase.getPurchaseDate()));
				}
			};
			
			add(new PagingNavigation("navigator", dataView));
		}
	}
}
