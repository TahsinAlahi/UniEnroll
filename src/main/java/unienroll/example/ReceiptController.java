package unienroll.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import unienroll.application.CourseService;
import unienroll.domain.Course;
import unienroll.domain.Student;
import unienroll.ui.SessionState;

import java.net.URL;
import java.util.ResourceBundle;

public class ReceiptController implements Initializable {

    @FXML
    private TextArea receiptArea;

    @FXML
    private Label totalCreditsLabel;

    @FXML
    private Label totalFeeLabel;

    private final SessionState sessionState;
    private final CourseService courseService;
    private final unienroll.ui.scenes.NavigationHandler navigationHandler;

    public ReceiptController(CourseService courseService, unienroll.ui.scenes.NavigationHandler navigationHandler) {
        this.sessionState = SessionState.getInstance();
        this.courseService = courseService;
        this.navigationHandler = navigationHandler;
    }

    @FXML
    private void goToDashboard() {
        navigationHandler.showDashboard();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Student student = (Student) sessionState.getLoggedInMember();
        if (student == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append("UniEnroll Course Management System\n");
        sb.append("----------------------------------\n");
        sb.append("Student Name: ").append(student.getName()).append("\n");
        sb.append("Student ID:   ").append(student.getId()).append("\n\n");
        sb.append(String.format("%-10s | %-25s | %-8s | %-8s\n", "Code", "Title", "Credits", "Fee"));
        sb.append("----------------------------------------------------------\n");

        double totalCredits = 0;
        double totalFee = 0;

        for (String courseId : student.getEnrolledCoursesId()) {
            Course course = courseService.listAllCourses().stream()
                    .filter(c -> c.getCourseId().equals(courseId))
                    .findFirst()
                    .orElse(null);
            
            if (course != null) {
                sb.append(String.format("%-10s | %-25s | %-8.1f | %-8.1f\n", 
                        course.getCourseId().substring(0, 8), 
                        course.getTitle(), 
                        course.getCredits(), 
                        course.getFee()));
                totalCredits += course.getCredits();
                totalFee += course.getFee();
            }
        }

        sb.append("----------------------------------------------------------\n");
        receiptArea.setText(sb.toString());
        totalCreditsLabel.setText("Total Credits: " + totalCredits);
        totalFeeLabel.setText("Total Fee: $" + totalFee);
    }
}