
package unienroll.ui.controller;

import unienroll.domain.Member;

public class UserSession {
    private static UserSession instance;
    private Member loggedInUser;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void login(Member user) {
        this.loggedInUser = user;
    }

    public void logout() {
        this.loggedInUser = null;
    }

    public Member getLoggedInUser() {
        return loggedInUser;
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }
}

