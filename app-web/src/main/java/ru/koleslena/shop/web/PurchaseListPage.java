package ru.koleslena.shop.web;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.koleslena.shop.orm.dto.Purchase;
import ru.koleslena.shop.orm.dto.Role;
import ru.koleslena.shop.web.provider.PagerDataProvider;

public class PurchaseListPage extends BasePage {
	private static final Logger logger = LoggerFactory.getLogger(PurchaseListPage.class);
	
	public PurchaseListPage() {
		super();
		
		if(getWicketWebSession().hasRole(Role.STRING_ADMIN_ROLE_NAME)) {
			IDataProvider<Purchase> dataProvider = new PagerDataProvider<Purchase>(Purchase.class);
			
			DataView<Purchase> dataView = new DataView<Purchase>("purchases", dataProvider, 4) {
				@Override
				protected void populateItem(final Item<Purchase> item) {
					final Purchase purchase = item.getModelObject();
					item.add(new Label("goods", purchase.getGoods().getName()));
					item.add(new Label("user", purchase.getUser().getName()));
					item.add(new Label("price", purchase.getPurchasePrice()));
					item.add(new Label("description", purchase.getPurchaseDate()));
				}
			};
			
			add(dataView);
			
			add(new PagingNavigator("navigator", dataView));
		}
	}
}
