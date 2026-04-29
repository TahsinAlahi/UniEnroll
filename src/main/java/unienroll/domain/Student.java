package unienroll.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student extends Member {
    private final List<String> enrolledCoursesId;
    private String major;
    private double cgpa;

    // Normal constructor
    public Student(String name, String email, String password) {
        super(name, email, password, Roles.STUDENT);
        this.enrolledCoursesId = new ArrayList<>();
        this.major = "";
        this.cgpa = 0.0;
    }

    //    When Jackson deserializes JSON to create a Student object
    //    This will insert the course list from the database to the memory while deserializing
    @JsonCreator
    public Student(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("isVerified") boolean isVerified,
            @JsonProperty("enrolledCoursesId") List<String> enrolledCoursesId,
            @JsonProperty("major") String major,
            @JsonProperty("cgpa") double cgpa
    ) {
        super(id, name, email, password, Roles.STUDENT, isVerified);
        this.enrolledCoursesId = enrolledCoursesId != null ? enrolledCoursesId : new ArrayList<>();
        this.major = major != null ? major : "";
        this.cgpa = cgpa;
    }

    public List<String> getEnrolledCoursesId() {
        //      TODO: This is a read-only view of the enrolled courses
        return Collections.unmodifiableList(enrolledCoursesId);
    }

    public void enrollCourse(String courseId) {
        this.enrolledCoursesId.add(courseId);
    }

    public void dropCourse(String courseId) {
        this.enrolledCoursesId.remove(courseId);
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", verified=" + getIsVerified() +
                ", enrolledCourses=" + enrolledCoursesId +
                ", major='" + major + '\'' +
                ", cgpa=" + cgpa +
                ", password='" + getPassword() + '\'' +
                '}';
    }
}
