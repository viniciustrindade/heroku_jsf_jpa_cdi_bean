package org.application.view;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlInputText;
import javax.inject.Inject;
import javax.inject.Named;

import org.application.FacesUtil;
import org.application.bean.BookmarkDao;
import org.application.model.User;


/**
 * This is the backing bean for the login form username/password values and uses
 * the injected {@link BookmarkDao} and the {@link UserSession} beans to check
 * the login credentials and set the login state.
 * 
 * @author Andy Gibson
 * 
 */
@RequestScoped
@Named
public class LoginBean {

	private String username = "andygibson";
	private String password = "mypass";

	@Inject
	private BookmarkDao bookmarkDao;

	@Inject
	private UserSession userSession;

	HtmlInputText usernameEntry;

	public void login() {
		User user = bookmarkDao.login(username, password);
		if (user == null) {
			FacesUtil.addError("Incorrect username/password", usernameEntry
					.getClientId());
		} else {
			userSession.setUser(user);
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public HtmlInputText getUsernameEntry() {
		return usernameEntry;
	}

	public void setUsernameEntry(HtmlInputText usernameEntry) {
		this.usernameEntry = usernameEntry;
	}
}
