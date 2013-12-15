package ru.koleslena.shop.web;

import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.koleslena.shop.exception.ShopException;
import ru.koleslena.shop.service.UserService;


public class RegistrationPage extends BasePage {
	
	private static final Logger logger = LoggerFactory.getLogger(RegistrationPage.class);

	@SpringBean
	private UserService userService;
	
	public RegistrationPage(PageParameters parameters) {
		super();
		add(new RegistrationForm("registrationForm"));
	}

	private class RegistrationForm extends StatelessForm<Void> {
		private String username;

		private String password;
		
		private String passwordAgain;

		public RegistrationForm(String id) {
			super(id);
			setModel(new CompoundPropertyModel(this));
			add(new RequiredTextField<String>("username"));
			
			PasswordTextField pass = new PasswordTextField("password");
			PasswordTextField passAgain = new PasswordTextField("passwordAgain");
			add(pass);
			add(passAgain);
			add(new EqualPasswordInputValidator(pass, passAgain));
		}

		@Override
		protected void onSubmit() {
			try {
				userService.createUser(username, password);
				setResponsePage(LoginPage.class);
			} catch (ShopException e) {
				logger.error("Registration failed: {} ", e.getMessage());
				getPage().error(e.getMessage());
			}
		}
	}
}
