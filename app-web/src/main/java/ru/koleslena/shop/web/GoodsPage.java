package ru.koleslena.shop.web;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.koleslena.shop.orm.dto.Goods;
import ru.koleslena.shop.orm.dto.Role;
import ru.koleslena.shop.service.GoodsService;

public class GoodsPage extends BasePage {
	
	private static final Logger logger = LoggerFactory.getLogger(GoodsPage.class);

	@SpringBean
	private GoodsService goodsService;
	
	public GoodsPage(PageParameters parameters) {
		super();
		Goods goods = null;
		StringValue strVal = parameters.get("id");
		Long id = (strVal == null || strVal.isNull() || strVal.isEmpty()) ? null : strVal.toLongObject();
		if(id != null) {
			goods = goodsService.get(id);
		}
		if(goods == null)
			goods = new Goods();
		
		IModel<Goods> model = new CompoundPropertyModel<Goods>(goods);
		
		add(new GoodsForm("editForm", model));
	}

	private class GoodsForm extends Form<Goods> {
		
		public GoodsForm(String id, IModel<Goods> model) {
			super(id, model);
			add(new RequiredTextField<String>("name"));
			add(new RequiredTextField<String>("descr"));
			add(new RequiredTextField<Double>("price", Double.class));
			add(new RequiredTextField<Integer>("count", Integer.class));
		}

		@Override
		protected void onSubmit() {
			if(getWicketWebSession().hasRole(Role.STRING_ADMIN_ROLE_NAME)) {
				Goods goods = (Goods) getDefaultModelObject();
				if(goods.getId() == null)
					goodsService.createGoods(goods);
				else
					goodsService.updateGoods(goods);
				setResponsePage(GoodsListPage.class);
			} else 
				getPage().error("You do not have rights!");
		}
	}
}
