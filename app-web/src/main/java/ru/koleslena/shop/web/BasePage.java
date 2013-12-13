package ru.koleslena.shop.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import ru.koleslena.shop.web.security.SpringWicketWebSession;

public class BasePage extends WebPage {

	public BasePage() {
		add(new LogoutPanel("logoutPanel"));
		add(new LoginPanel("loginPanel"));		
	}
	
	public SpringWicketWebSession getWicketWebSession() {
		return (SpringWicketWebSession) SpringWicketWebSession.get();
	}
	
	private class LoginPanel extends Panel {
		public LoginPanel(String id) {
			super(id);
			
			Link<Void> loginLink = new Link<Void>("loginLink"){
				@Override
				public void onClick() {
					setResponsePage(LoginPage.class);				
				}
				
			};
			add(loginLink);
			
			Link<Void> registrationLink = new Link<Void>("registrationLink"){
				@Override
				public void onClick() {
					setResponsePage(RegistrationPage.class);				
				}
				
			};
			add(registrationLink);
		}
		
		@Override
		public boolean isVisible() {
			return getWicketWebSession().isSignedIn();
		}
	}
	
	private class LogoutPanel extends Panel {
		public LogoutPanel(String id) {
			super(id);
			Label name = new Label(getWicketWebSession().getCurrentUser().getName());
			add(name);
			
			Link<Void> logoutLink = new Link<Void>("logoutLink"){
				@Override
				public void onClick() {
					getWicketWebSession().invalidate();				
				}
				
			};
			add(logoutLink);
		}
		
		@Override
		public boolean isVisible() {
			return !getWicketWebSession().isSignedIn();
		}
	}
}
