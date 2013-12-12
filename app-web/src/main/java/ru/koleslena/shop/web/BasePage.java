package ru.koleslena.shop.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

import ru.koleslena.shop.web.security.SpringWicketWebSession;

public class BasePage extends WebPage {

	public BasePage() {
		
		SpringWicketWebSession session = (SpringWicketWebSession) getSession();
		
		if(session.isSignedIn()) {
			add(new Label(session.getCurrentUser().getName()));
			add(new Link<Void>("logoutLink"){
				@Override
				public void onClick() {
					getSession().invalidate();				
				}
				
			});
		} else {
			add(new Link<Void>("loginLink"){
				@Override
				public void onClick() {
					setResponsePage(LoginPage.class);				
				}
				
			});
			add(new Link<Void>("RegistrationLink"){
				@Override
				public void onClick() {
					setResponsePage(RegistrationPage.class);				
				}
				
			});
		}
	}
}
