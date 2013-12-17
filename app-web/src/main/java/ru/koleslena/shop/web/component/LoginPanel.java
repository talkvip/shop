package ru.koleslena.shop.web.component;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import ru.koleslena.shop.web.LoginPage;
import ru.koleslena.shop.web.RegistrationPage;
import ru.koleslena.shop.web.security.SpringWicketWebSession;

/**
 * @author koleslena
 *
 */
public class LoginPanel extends Panel {
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
		return !getWicketWebSession().isSignedIn();
	}
	
	public SpringWicketWebSession getWicketWebSession() {
		return (SpringWicketWebSession) SpringWicketWebSession.get();
	}
}
