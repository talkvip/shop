package ru.koleslena.shop.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.savedrequest.SavedRequest;

public class LoginPage extends BasePage {
	
	private static Logger logger = LoggerFactory.getLogger(LoginPage.class);

	public LoginPage(PageParameters parameters) {
		super();
		add(new LoginForm("loginForm"));
	}

	private class LoginForm extends StatelessForm<Void> {

		//private transient RequestCache requestCache = new HttpSessionRequestCache();

		private String username;

		private String password;

		public LoginForm(String id) {
			super(id);
			setModel(new CompoundPropertyModel(this));
			add(new RequiredTextField<String>("username"));
			add(new PasswordTextField("password"));
			//add(new LoginValidator(username, password));
		}

		@Override
		protected void onSubmit() {
			HttpServletRequest servletRequest = (HttpServletRequest) RequestCycle.get().getRequest().getContainerRequest();
			String originalUrl = getOriginalUrl(servletRequest.getSession());
			if (getWicketWebSession().signIn(username, password)) {
				if (originalUrl != null) {
					logger.info(String.format("redirecting to %s", originalUrl));
					throw new RedirectToUrlException(originalUrl);
				} else {
					logger.info("redirecting to home page");
					setResponsePage(getApplication().getHomePage());
				}
			} else {
				getPage().error("Login failed due to invalid credentials");
			}
		}

		/**
		 * Returns the URL the user accessed before he was redirected to the login page. This URL has been stored in the session by spring
		 * security.
		 * 
		 * @return the original URL the user accessed or null if no URL has been stored in the session.
		 */
		private String getOriginalUrl(HttpSession session) {
			// TODO: The following session attribute seems to be null the very first time a user accesses a secured page. Find out why
			// spring security doesn't set this parameter the very first time. 
			// TODO: KL: may be see requestCache
			SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
			if (savedRequest != null) {
				return savedRequest.getRedirectUrl();
			} else {
				return null;
			}
		}

	}

}
