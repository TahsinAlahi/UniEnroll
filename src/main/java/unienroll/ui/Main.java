package unienroll.ui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import unienroll.application.CourseService;
import unienroll.application.MemberService;
import unienroll.application.RegistrationWindowService;
import unienroll.domain.Admin;
import unienroll.domain.Student;
import unienroll.infrastructure.file.FileCourseRepository;
import unienroll.infrastructure.file.FileMemberRepository;
import unienroll.infrastructure.file.FileRegistrationWindowRepository;

import java.time.LocalDateTime;


public class Main extends Application {
    
    @Override
    public void start(Stage stage) {
        try {
            // Initialize Backend Services
            FileMemberRepository memberRepo = FileMemberRepository.getInstance();
            FileCourseRepository courseRepo = FileCourseRepository.getInstance();
            FileRegistrationWindowRepository windowRepo = FileRegistrationWindowRepository.getInstance();

            RegistrationWindowService windowService = new RegistrationWindowService(windowRepo);
            MemberService memberService = new MemberService(memberRepo);
            CourseService courseService = new CourseService(courseRepo, memberRepo, windowService);

            // Register in ServiceLocator
            ServiceLocator locator = ServiceLocator.getInstance();
            locator.registerMemberService(memberService);
            locator.registerCourseService(courseService);
            locator.registerWindowService(windowService);

            // Set up Demo Data
            setupDemoData(memberService, courseService, windowService);
           
            Parent root1 = FXMLLoader.load(getClass().getResource("Data_input.fxml"));
            Scene scene1 = new Scene(root1);
        
            stage.setWidth(800);   
            stage.setHeight(450); 
           
            stage.setTitle("Student Login");
           
            stage.getIcons().add(new Image(getClass().getResourceAsStream("Human_icon.jpg")));
           
            stage.setResizable(true);
           
            stage.setScene(scene1);
            stage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void setupDemoData(MemberService memberService, CourseService courseService, RegistrationWindowService windowService) {
        try {
            // Active registration window
            windowService.setRegistrationWindow(LocalDateTime.now().minusMinutes(10), LocalDateTime.now().plusHours(24));

            // Demo Admin
            Admin admin = new Admin("System Admin", "admin@northsouth.edu", "admin123");
            if (memberService.findByEmail(admin.getEmail()) == null) {
                memberService.registerMember(admin);
                admin.setVerified(true);
                FileMemberRepository.getInstance().update(admin);
            }

            // Demo Student
            Student student = new Student("Tahsin Alahi", "student@northsouth.edu", "pass123");
            if (memberService.findByEmail(student.getEmail()) == null) {
                memberService.registerMember(student);
                memberService.approveMember(student.getId()); // Auto-verify for demo convenience
            }

            // Demo Courses if none exist
            if (courseService.listAllCourses().isEmpty()) {
                String adminId = memberService.findByEmail("admin@northsouth.edu").getId();
                courseService.createCourse("CSE215", "Programming Language II", adminId, 30, 3.0, 19500.0);
                courseService.createCourse("CSE215L", "Programming Language II Lab", adminId, 30, 1.0, 6500.0);
                courseService.createCourse("CSE225", "Data Structures", adminId, 30, 3.0, 19500.0);
            }
        } catch (Exception e) {
            System.err.println("Note: Demo data setup skipped or already exists: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}

