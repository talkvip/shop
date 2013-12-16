package ru.koleslena.shop.web;

import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author koleslena
 *
 */
public class LoginPage extends BasePage {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

	public LoginPage(PageParameters parameters) {
		super();
		add(new LoginForm("loginForm"));
	}

	private class LoginForm extends StatelessForm<Void> {

		private String username;

		private String password;

		public LoginForm(String id) {
			super(id);
			setModel(new CompoundPropertyModel(this));
			add(new RequiredTextField<String>("username"));
			add(new PasswordTextField("password"));
		}

		@Override
		protected void onSubmit() {
			if (getWicketWebSession().signIn(username, password)) {
				logger.info("redirecting to home page");
				setResponsePage(getApplication().getHomePage());
			} else {
				getPage().error("Login failed due to invalid credentials");
			}
		}
	}
}
