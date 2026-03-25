package unienroll.domain;

public class Admin extends Member {
    public Admin(String name, String email, String password) {
        super(name, email, password,Roles.ADMIN);
    }

    public void approveUser(Member user) {
        user.setVerified(true);
    }
}
