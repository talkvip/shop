package ru.koleslena.shop.web;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.koleslena.shop.exception.ShopException;
import ru.koleslena.shop.orm.dto.Goods;
import ru.koleslena.shop.orm.dto.Role;
import ru.koleslena.shop.service.GoodsService;
import ru.koleslena.shop.service.PurchaseService;
import ru.koleslena.shop.web.provider.PagerDataProvider;
import ru.koleslena.shop.web.security.SpringWicketWebSession;

public class GoodsListPage extends BasePage {
	
	private final Logger logger = LoggerFactory.getLogger(GoodsListPage.class);
	
	@SpringBean
	private PurchaseService purchaseService;

	@SpringBean
	private GoodsService goodsService;
	
	public GoodsListPage() {
		super();
		IDataProvider<Goods> dataProvider = new PagerDataProvider<Goods>(Goods.class);
		
		DataView<Goods> dataView = new DataView<Goods>("rows", dataProvider, 20) {
			@Override
			protected void populateItem(final Item<Goods> item) {
				final Goods good = item.getModelObject();
				item.add(new Label("name", good.getName()));
				item.add(new Label("price", good.getPrice()));
				item.add(new Label("description", good.getDescr()));
				
				SpringWicketWebSession session = (SpringWicketWebSession) getSession();
				
				if(session.hasRole(Role.STRING_USER_ROLE_NAME)) {
					Button buy = new Button("buy") {
						@Override
						public void onSubmit() {
							SpringWicketWebSession session = (SpringWicketWebSession) getSession();
							
							if(session.hasRole(Role.STRING_USER_ROLE_NAME)) {
								try {
									purchaseService.createPurchase(session.getCurrentUser(), item.getModelObject());
								} catch (ShopException exc) {
									logger.error("trying to buy. Error {}", exc.getMessage());
									getPage().error(exc.getMessage());
								}
								return;
							}
							getPage().error("You are not authorized");
						}
					};
					item.add(buy);
				}
				
				if(session.hasRole(Role.STRING_ADMIN_ROLE_NAME)) {
					Button edit = new Button("edit") {
						@Override
						public void onSubmit() {
							SpringWicketWebSession session = (SpringWicketWebSession) getSession();
							
							if(session.hasRole(Role.STRING_ADMIN_ROLE_NAME)) {
								PageParameters pageParameters = new PageParameters();
								pageParameters.add("id", item.getModelObject().getId());

								setResponsePage(GoodsPage.class, pageParameters);
							} else
								getPage().error("You do not have rights!");
						}
					};
					item.add(edit);
					
					Button delete = new Button("delete") {
						@Override
						public void onSubmit() {
							SpringWicketWebSession session = (SpringWicketWebSession) getSession();
							
							if(session.hasRole(Role.STRING_ADMIN_ROLE_NAME)) {
								goodsService.deleteGoods(item.getModelObject());
							} else
								getPage().error("You do not have rights!");
						}
					};
					item.add(edit);
				}
				
				item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public String getObject() {
                        return (item.getIndex() % 2 == 1) ? "even" : "odd";
                    }
			    }));
			}
		};  
		
		add(dataView);
		
		SpringWicketWebSession session = (SpringWicketWebSession) getSession();
		
		if(session.hasRole(Role.STRING_ADMIN_ROLE_NAME)) {
			Button create = new Button("create") {
				@Override
				public void onSubmit() {
					SpringWicketWebSession session = (SpringWicketWebSession) getSession();
					
					if(session.hasRole(Role.STRING_ADMIN_ROLE_NAME)) {
						setResponsePage(GoodsPage.class);
					} else 
						getPage().error("You do not have rights!");
				}
			};
			add(create);
		}
		
		add(new PagingNavigation("navigator", dataView));
	}
}
