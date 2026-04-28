package unienroll.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.UUID;
import unienroll.exception.ValidationException;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "role", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Admin.class, name = "ADMIN"),
        @JsonSubTypes.Type(value = Student.class, name = "STUDENT")
})
public class Member {

    //    TODO: Make it thread safe
    private final String id;
    private String name;
    private final String email;
    private String password;
    private final Roles role;
    private boolean isVerified = false;

    public Member(
           String name,
           String email,
           String password,
           Roles role
    ) {
        this.id = UUID.randomUUID().toString();
        this.email = email;
        setName(name);
        setPassword(password);
        this.role = role;
    }

    @JsonCreator
    public Member(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("role") Roles role,
            @JsonProperty("isVerified") boolean isVerified
    ) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.email = email;
        setName(name);
        setPassword(password);
        this.role = role;
        this.isVerified = isVerified;
    }

    public Roles getRole() {
        return role;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name cannot be empty");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("Password cannot be empty");
        }
        this.password = password;
    }

    @JsonProperty("password")
    protected String getPassword() {
        return password;
    }

    public boolean isValidPassword( String password){
        return this.password.equals(password);
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
                ", password='" + password + '\'' +
                '}';
    }
}
