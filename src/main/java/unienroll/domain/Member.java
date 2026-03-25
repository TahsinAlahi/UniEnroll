package unienroll.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "role", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Admin.class, name = "ADMIN"),
        @JsonSubTypes.Type(value = Student.class, name = "STUDENT")
})
public class Member {
    private static long counter = 100L;

    //    TODO: Make it thread safe
    private final String id;
    private String name;
    private final String email;
    private String password;
    private final Roles role;
    private boolean isVerified = false;

    public Member(
            @JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("role") Roles role
    ) {
        this.id = "USR-" + counter++;
        this.email = email;
        setName(name);
        setPassword(password);
        this.role = role;
    }

    public Roles getRole() {
        return role;
    }

    public String getId() {
        return id;
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
