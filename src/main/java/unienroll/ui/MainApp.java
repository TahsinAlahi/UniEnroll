package unienroll.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import unienroll.application.CourseService;
import unienroll.application.MemberService;
import unienroll.infrastructure.file.FileCourseRepository;
import unienroll.infrastructure.file.FileMemberRepository;
import unienroll.ui.controllers.AuthController;
import unienroll.ui.controllers.DashboardController;
import unienroll.ui.scenes.DashboardView;
import unienroll.ui.scenes.LoginView;
import unienroll.ui.scenes.NavigationHandler;
import unienroll.ui.scenes.RegisterView;

public class MainApp extends Application implements NavigationHandler {

    private Stage primaryStage;
    
    // Dependencies
    private AuthController authController;
    private DashboardController dashboardController;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("UniEnroll Course Management System");

        // Initialize Use Cases & Repositories
        FileMemberRepository memberRepo = FileMemberRepository.getInstance();
        FileCourseRepository courseRepo = FileCourseRepository.getInstance();

        MemberService memberService = new MemberService(memberRepo);
        CourseService courseService = new CourseService(courseRepo, memberRepo);

        this.authController = new AuthController(memberService);
        this.dashboardController = new DashboardController(memberService, courseService);

        // Show default scene
        showLogin();
        this.primaryStage.show();
    }

    @Override
    public void showLogin() {
        LoginView loginView = new LoginView(authController, this);
        primaryStage.setScene(loginView.getScene());
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
    }

    public static void main(String[] args) {
        launch(args);
    }
}
