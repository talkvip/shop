package ru.koleslena.shop.web;

import java.io.Serializable;
import java.math.BigInteger;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.koleslena.shop.exception.ShopException;
import ru.koleslena.shop.orm.dto.Goods;
import ru.koleslena.shop.orm.dto.Role;
import ru.koleslena.shop.orm.dto.User;
import ru.koleslena.shop.service.GoodsService;
import ru.koleslena.shop.service.PurchaseService;
import ru.koleslena.shop.web.provider.PagerDataProvider;

/**
 * @author koleslena
 *
 */
public class GoodsListPage extends BasePage {
	
	private static final Logger logger = LoggerFactory.getLogger(GoodsListPage.class);
	
	@SpringBean
	private PurchaseService purchaseService;

	@SpringBean
	private GoodsService goodsService;
	
	public GoodsListPage() {
		super();
		IDataProvider<Goods> dataProvider = new PagerDataProvider<Goods>(Goods.class);
		
		DataView<Goods> dataView = new DataView<Goods>("rows", dataProvider, 5) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void populateItem(final Item<Goods> item) {
				final Goods good = item.getModelObject();
				item.add(new Label("name", good.getName()));
				item.add(new Label("price", good.getPrice()));
				item.add(new Label("count", good.getCount()));
				item.add(new Label("description", good.getDescr()));
				
				BuyItem buyItem = new BuyItem(good.getId());
				IModel<BuyItem> formModel = new CompoundPropertyModel<BuyItem>(buyItem);
				
				Form<BuyItem> buyForm = new Form<BuyItem>("buyForm", formModel) {
					@Override
					public boolean isVisible() {
						return getWicketWebSession().hasRole(Role.STRING_USER_ROLE_NAME);
					}
					
					@Override
					protected void onSubmit() {
						if(getWicketWebSession().hasRole(Role.STRING_USER_ROLE_NAME)) {
							try {
								BuyItem buyIt = getModelObject();
								User currentUser = getWicketWebSession().getCurrentUser();
								purchaseService.createPurchase(currentUser.getId(), buyIt.getId(), buyIt.getNumber());
							} catch (ShopException exc) {
								logger.error("trying to buy. Error {}", exc.getMessage());
								getPage().error(exc.getMessage());
							}
							return;
						}
						getPage().error("You are not authorized");
					}
				};
				
				TextField<Integer> number = new RequiredTextField<Integer>("number", Integer.class);
				
				buyForm.add(number);
				
				item.add(buyForm);
				
				Link<Goods> edit = new Link<Goods>("edit", item.getModel()) {
					@Override
					public void onClick() {
						if(getWicketWebSession().hasRole(Role.STRING_ADMIN_ROLE_NAME)) {
							Goods goods = getModelObject();
							
							PageParameters pageParameters = new PageParameters();
							pageParameters.add("id", goods.getId());

							setResponsePage(GoodsPage.class, pageParameters);
						} else
							getPage().error("You do not have rights!");
					}
					
					@Override
					public boolean isVisible() {
						return getWicketWebSession().hasRole(Role.STRING_ADMIN_ROLE_NAME);
					}
				};
				item.add(edit);
				
				Link<Goods> delete = new Link<Goods>("delete", item.getModel()) {
					@Override
					public void onClick() {
						if(getWicketWebSession().hasRole(Role.STRING_ADMIN_ROLE_NAME)) {
							Goods goods = getModelObject();
							goodsService.deleteGoods(goods);
						} else
							getPage().error("You do not have rights!");
					}
					
					@Override
					public boolean isVisible() {
						return getWicketWebSession().hasRole(Role.STRING_ADMIN_ROLE_NAME);
					}
				};
				item.add(delete);
				
				item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public String getObject() {
                        return (item.getIndex() % 2 == 1) ? "even" : "odd";
                    }
			    }));
			}
		};  
		
		Link<Void> create = new Link<Void>("create") {
			@Override
			public void onClick() {
				if(getWicketWebSession().hasRole(Role.STRING_ADMIN_ROLE_NAME)) {
					setResponsePage(GoodsPage.class, new PageParameters());
				} else 
					getPage().error("You do not have rights!");
			}
			
			@Override
			public boolean isVisible() {
				return getWicketWebSession().hasRole(Role.STRING_ADMIN_ROLE_NAME);
			}
		};
		add(create);
		
		Link<Void> toPurchaseList = new Link<Void>("toPurchaseList") {
			@Override
			public void onClick() {
				if(getWicketWebSession().hasRole(Role.STRING_ADMIN_ROLE_NAME)) {
					setResponsePage(PurchaseListPage.class);
				} else 
					getPage().error("You do not have rights!");
			}
			
			@Override
			public boolean isVisible() {
				return getWicketWebSession().hasRole(Role.STRING_ADMIN_ROLE_NAME);
			}
		};
		add(toPurchaseList);
		
		add(dataView);
		
		add(new PagingNavigator("navigator", dataView));
	}
	
	class BuyItem implements Serializable {
		private Long id;
		private BigInteger number;
		
		public BuyItem(Long id) {
			super();
			this.id = id;
			this.number = BigInteger.ONE;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public BigInteger getNumber() {
			return number;
		}
		public void setNumber(BigInteger number) {
			this.number = number;
		}
	}
}
