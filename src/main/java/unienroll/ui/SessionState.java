package unienroll.ui;

import unienroll.domain.Member;

public class SessionState {
    private static SessionState instance;
    private Member loggedInMember;

    private String selectedDepartment;
    private String selectedMajor;

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

    public String getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(String selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }

    public String getSelectedMajor() {
        return selectedMajor;
    }

    public void setSelectedMajor(String selectedMajor) {
        this.selectedMajor = selectedMajor;
    }

    public void clear() {
        this.loggedInMember = null;
        this.selectedDepartment = null;
        this.selectedMajor = null;
    }
}
