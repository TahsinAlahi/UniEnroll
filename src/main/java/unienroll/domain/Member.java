package unienroll.domain;

public class Member {
    private String name;
    private final String email;
    private String password;
    private boolean isVerified = false;

    public Member(String email, String name, String password) {
        this.email = email;
        setName(name);
        setPassword(password);
    }

    //TODO: setup validation & format
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsVerified() {
        return isVerified;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isVerified=" + isVerified +
                '}';
    }
}
