package unienroll.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import unienroll.exception.ValidationException;

public class Course {
    private final String courseId;
    private String title;
    private String description;
    //    TODO: Make instructorId a reference to the instructor
    private String instructorId;
    private List<String> enrolledStudentsId;
    private int capacity;

    // default constructor
    public Course(String title, String description, String instructorId, int capacity) {
        setTitle(title);
        setDescription(description);
        setInstructorId(instructorId);
        setCapacity(capacity);
        this.enrolledStudentsId = new ArrayList<>();
        this.courseId = UUID.randomUUID().toString();
    }

    @JsonCreator
    public Course(
            @JsonProperty("courseId") String courseId,
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("instructorId") String instructorId,
            @JsonProperty("capacity") int capacity,
            @JsonProperty("enrolledStudentsId") List<String> enrolledStudentsId
    ) {
        setTitle(title);
        setDescription(description);
        setInstructorId(instructorId);
        setCapacity(capacity);
        this.enrolledStudentsId = enrolledStudentsId != null ? enrolledStudentsId : new ArrayList<>();
        this.courseId = courseId != null ? courseId : UUID.randomUUID().toString();
    }

    public List<String> getEnrolledStudentsId() {
        return enrolledStudentsId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new ValidationException("Course title cannot be empty");
        }
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new ValidationException("Capacity must be greater than zero");
        }
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void enrollStudent(String studentId) {
        //    TODO: Don't know how to catch this error yet but putting it here for good measure
        if (!hasAvailableSeat()) {
            throw new IllegalStateException("Course is full");
        }
        enrolledStudentsId.add(studentId);
    }

    public void removeStudent(String studentId) {
        enrolledStudentsId.remove(studentId);
    }

    public boolean hasAvailableSeat() {
        return capacity > enrolledStudentsId.size();
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", instructorId='" + instructorId + '\'' +
                ", capacity=" + capacity +
                ", enrolledStudentsCount=" + enrolledStudentsId.size() +
                ", enrolledStudentsId=" + enrolledStudentsId +
                '}';
    }
}
