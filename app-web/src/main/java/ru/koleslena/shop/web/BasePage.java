package ru.koleslena.shop.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import ru.koleslena.shop.web.security.SpringWicketWebSession;

/**
 * @author koleslena
 *
 */
public class BasePage extends WebPage {

	public BasePage() {
		add(new LogoutPanel("logoutPanel"));
		add(new LoginPanel("loginPanel"));
		add(new FeedbackPanel("feedback"));
	}
	
	public SpringWicketWebSession getWicketWebSession() {
		return (SpringWicketWebSession) SpringWicketWebSession.get();
	}
	
	

}
