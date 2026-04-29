package unienroll.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import unienroll.application.CourseService;
import unienroll.application.MemberService;
import unienroll.application.RegistrationWindowService;
import unienroll.infrastructure.file.FileCourseRepository;
import unienroll.infrastructure.file.FileMemberRepository;
import unienroll.infrastructure.file.FileRegistrationWindowRepository;
import unienroll.ui.controllers.AuthController;
import unienroll.ui.controllers.DashboardController;
import unienroll.ui.scenes.DashboardView;
import unienroll.ui.scenes.LoginView;
import unienroll.ui.scenes.NavigationHandler;
import unienroll.ui.scenes.RegisterView;

public class MainApp extends Application implements NavigationHandler {

    private static MainApp instance;
    private Stage primaryStage;
    private static AuthController authController;
    private static DashboardController dashboardController;
    private static unienroll.ui.controllers.CourseController courseController;
    private static unienroll.application.CourseService courseService;
    private static unienroll.application.MemberService memberService;

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("UniEnroll Course Management System");

        // Initialize Use Cases & Repositories
        FileMemberRepository memberRepo = FileMemberRepository.getInstance();
        FileCourseRepository courseRepo = FileCourseRepository.getInstance();
        FileRegistrationWindowRepository windowRepo = FileRegistrationWindowRepository.getInstance();

        RegistrationWindowService windowService = new RegistrationWindowService(windowRepo);
        memberService = new MemberService(memberRepo);
        courseService = new unienroll.application.CourseService(courseRepo, memberRepo, windowService);

        authController = new AuthController(memberService);
        dashboardController = new DashboardController(memberService, courseService, windowService);
        courseController = new unienroll.ui.controllers.CourseController(courseService);

        // Show default scene
        showLogin();
        this.primaryStage.show();
    }

    public static javafx.util.Callback<Class<?>, Object> getControllerFactory() {
        return type -> {
            if (type == unienroll.example.Data_input_Controller.class) {
                return new unienroll.example.Data_input_Controller(authController, instance);
            } else if (type == unienroll.example.Student_info_controller.class) {
                return new unienroll.example.Student_info_controller(memberService, courseService);
            }
 else if (type == unienroll.example.Department_selection_controller.class) {
                return new unienroll.example.Department_selection_controller();
            } else if (type == unienroll.example.Major_selection_controller.class) {
                return new unienroll.example.Major_selection_controller();
            } else if (type == unienroll.example.Course_selection_controller.class) {
                return new unienroll.example.Course_selection_controller(courseController);
            } else if (type == unienroll.example.ReceiptController.class) {
                return new unienroll.example.ReceiptController(courseService, instance);
            }
            try {
                return type.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        };
    }

    @Override
    public void showLogin() {
        showTeamUI();
    }

    @Override
    public void showRegister() {
        RegisterView registerView = new RegisterView(authController, this);
        primaryStage.setScene(registerView.getScene());
    }

    @Override
    public void showDashboard() {
        DashboardView dashboardView = new DashboardView(dashboardController, authController, this);
        primaryStage.setScene(dashboardView.getScene());
        primaryStage.setResizable(true);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
    }

    public void showStudentInfo() {
        loadTeamFXML("Student_info.fxml", "Student Information");
    }

    public void showTeamUI() {
        loadTeamFXML("Data_input.fxml", "Student Enrollment");
    }

    private void loadTeamFXML(String fxml, String title) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                unienroll.example.Main.class.getResource(fxml)
            );
            loader.setControllerFactory(getControllerFactory());
            javafx.scene.Parent root = loader.load();
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen();
            primaryStage.setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
