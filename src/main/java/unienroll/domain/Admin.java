package unienroll.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Admin extends Member {
    //    Normal constructor
    public Admin(String name, String email, String password) {
        super(name, email, password, Roles.ADMIN);
    }

    //    Jackson constructor for deserialization
    @JsonCreator
    public Admin(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("isVerified") boolean isVerified) {
        super(id, name, email, password, Roles.ADMIN, isVerified);
    }

    public void approveUser(Member user) {
        user.setVerified(true);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", isVerified=" + getIsVerified() +
                ", password='" + getPassword() + '\'' +
                '}';
    }

}
