package unienroll.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import unienroll.application.CourseService;
import unienroll.domain.Course;
import unienroll.domain.Student;
import unienroll.ui.SessionState;

import java.util.List;
import java.util.stream.Collectors;

public class CourseController {
    private final CourseService courseService;
    private final SessionState sessionState;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
        this.sessionState = SessionState.getInstance();
    }

    public ObservableList<unienroll.example.Course> getCoursesForMajor(String major) {
        // In a real system, we'd filter by major in the repository or service.
        // For now, we'll return all courses and map them to the team's model.
        List<Course> domainCourses = courseService.listAllCourses();
        
        List<unienroll.example.Course> teamCourses = domainCourses.stream()
                .map(this::mapToTeamCourse)
                .collect(Collectors.toList());
        
        return FXCollections.observableArrayList(teamCourses);
    }

    public void enrollInCourses(List<unienroll.example.Course> selectedCourses) throws Exception {
        Student student = (Student) sessionState.getLoggedInMember();
        if (student == null) {
            throw new Exception("No student logged in.");
        }

        for (unienroll.example.Course teamCourse : selectedCourses) {
            // Find the domain course ID (we might need to store it in team's Course or map by code/title)
            // For now, let's assume 'code' in team's Course matches some logic or we find by title.
            // A better way is to add courseId to team's Course, but we want minimal changes.
            // Let's try to find by title.
            Course domainCourse = courseService.listAllCourses().stream()
                    .filter(c -> c.getTitle().equals(teamCourse.getTitle()))
                    .findFirst()
                    .orElse(null);

            if (domainCourse != null) {
                courseService.enrollStudent(student.getId(), domainCourse.getCourseId());
            }
        }
    }

    private unienroll.example.Course mapToTeamCourse(Course domainCourse) {
        // We'll generate a dummy code if needed, or use title as code if it's short.
        String code = domainCourse.getTitle().split(" ")[0]; 
        double credits = 3.0; // Default credits if not in domain
        double fee = 19500.0; // Default fee if not in domain
        
        return new unienroll.example.Course(
                code,
                domainCourse.getTitle(),
                credits,
                fee,
                false
        );
    }
    
    public double getStudentCgpa() {
        Student student = (Student) sessionState.getLoggedInMember();
        return student != null ? student.getCgpa() : 0.0;
    }
}
