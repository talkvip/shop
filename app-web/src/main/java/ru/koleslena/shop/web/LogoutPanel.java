package ru.koleslena.shop.web;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.koleslena.shop.web.security.SpringWicketWebSession;

public class LogoutPanel extends Panel {
	
	private static final Logger logger = LoggerFactory.getLogger(LogoutPanel.class);
	
	public LogoutPanel(String id) {
		super(id);
		Label name = new Label("userName", new PropertyModel<String>(this, "userName"));
		add(name);
		
		Link<Void> logoutLink = new Link<Void>("logoutLink"){
			@Override
			public void onClick() {
				getWicketWebSession().invalidate();				
			}
			
		};
		add(logoutLink);
	}
	
	public SpringWicketWebSession getWicketWebSession() {
		return (SpringWicketWebSession) SpringWicketWebSession.get();
	}
	
	public String getUserName() {
        if (getWicketWebSession().getCurrentUser() == null) {
            return "No Logged";
        } else {
            return getWicketWebSession().getCurrentUser().getName();
        }
    }
	
	@Override
	public boolean isVisible() {
		return getWicketWebSession().isSignedIn();
	}
}
