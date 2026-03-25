package unienroll.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student extends Member {
    private final List<String> enrolledCoursesId;

    public Student(String name, String email, String password) {
        super(name, email, password,Roles.STUDENT);
        this.enrolledCoursesId = new ArrayList<>();
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

    @Override
    public String toString() {
        return "Student{" +
                "name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", verified=" + getIsVerified() +
                ", enrolledCourses=" + enrolledCoursesId +
                '}';
    }
}
