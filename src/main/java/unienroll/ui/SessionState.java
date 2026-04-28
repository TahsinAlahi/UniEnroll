package unienroll.ui;

import unienroll.domain.Member;

public class SessionState {
    private static SessionState instance;
    private Member loggedInMember;

    private SessionState() {}

    public static SessionState getInstance() {
        if (instance == null) {
            instance = new SessionState();
        }
        return instance;
    }

    public Member getLoggedInMember() {
        return loggedInMember;
    }

    public void setLoggedInMember(Member member) {
        this.loggedInMember = member;
    }

    public void clear() {
        this.loggedInMember = null;
    }
}
