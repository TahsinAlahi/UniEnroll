package unienroll.ui.scenes;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import unienroll.domain.Course;
import unienroll.domain.Member;
import unienroll.domain.Roles;
import unienroll.ui.SessionState;
import unienroll.ui.controllers.AuthController;
import unienroll.ui.controllers.DashboardController;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardView {
    private final DashboardController dashboardController;
    private final AuthController authController;
    private final NavigationHandler navigationHandler;

    public DashboardView(DashboardController dashboardController, AuthController authController,
            NavigationHandler navigationHandler) {
        this.dashboardController = dashboardController;
        this.authController = authController;
        this.navigationHandler = navigationHandler;
    }

    public Scene getScene() {
        Member currentUser = SessionState.getInstance().getLoggedInMember();
        if (currentUser == null) {
            navigationHandler.showLogin();
            return new Scene(new VBox(), 800, 600);
        }

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f9f9f9; -fx-font-family: 'Inter', sans-serif;");

        // Top Navigation Bar
        HBox topBar = new HBox();
        topBar.setStyle("-fx-background-color: #2c3e50; -fx-padding: 15px;");
        topBar.setSpacing(20);

        Label welcomeLabel = new Label("Welcome, " + currentUser.getName() + " (" + currentUser.getRole() + ")");
        welcomeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");
        logoutButton.setOnAction(e -> {
            authController.logout();
            navigationHandler.showLogin();
        });

        topBar.getChildren().addAll(welcomeLabel, spacer, logoutButton);
        root.setTop(topBar);

        // Main Content Area
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));

        if (!currentUser.getIsVerified()) {
            Label unverifiedLabel = new Label(
                    "Your account is pending approval by an Admin.\nYou cannot access system features yet.");
            unverifiedLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #e67e22; -fx-font-weight: bold;");
            content.getChildren().add(unverifiedLabel);
        } else {
            if (currentUser.getRole() == Roles.ADMIN) {
                buildAdminDashboard(content);
            } else {
                buildStudentDashboard(content);
            }
        }

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        root.setCenter(scrollPane);

        return new Scene(root, 800, 600);
    }

    private void buildAdminDashboard(VBox content) {
        // Pending Members Section
        Label pendingLabel = new Label("Pending Members");
        pendingLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ListView<Member> pendingListView = new ListView<>();
        pendingListView.getItems().addAll(dashboardController.getPendingMembers());
        pendingListView.setPrefHeight(150);

        Button approveButton = new Button("Approve Selected");
        approveButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-cursor: hand;");
        approveButton.setOnAction(e -> {
            Member selected = pendingListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    dashboardController.approveMember(selected.getId());
                    pendingListView.getItems().remove(selected);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Member approved successfully.");
                } catch (Exception ex) {
                    showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
                }
            }
        });

        // Create Course Section
        Label courseLabel = new Label("Create New Course");
        courseLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField titleField = new TextField();
        titleField.setPromptText("Course Title");
        titleField.setStyle("-fx-padding: 8px;");

        TextField descField = new TextField();
        descField.setPromptText("Description");
        descField.setStyle("-fx-padding: 8px;");

        TextField capacityField = new TextField();
        capacityField.setPromptText("Capacity (e.g. 30)");
        capacityField.setStyle("-fx-padding: 8px;");

        Button createCourseBtn = new Button("Create Course");
        createCourseBtn.setStyle(
                "-fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand; -fx-padding: 8px 15px;");
        createCourseBtn.setOnAction(e -> {
            try {
                int capacity = Integer.parseInt(capacityField.getText());
                dashboardController.createCourse(titleField.getText(), descField.getText(), capacity);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Course created successfully.");
                titleField.clear();
                descField.clear();
                capacityField.clear();
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Capacity must be a valid number.");
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

        VBox createCourseBox = new VBox(10, courseLabel, titleField, descField, capacityField, createCourseBtn);
        createCourseBox.setStyle(
                "-fx-padding: 20px; -fx-background-color: white; -fx-background-radius: 8px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        content.getChildren().addAll(pendingLabel, pendingListView, approveButton, new Separator(), createCourseBox);
    }

    private void buildStudentDashboard(VBox content) {
        // Available Courses
        Label availableLabel = new Label("Available Courses");
        availableLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TableView<Course> availableTableView = new TableView<>();
        availableTableView.setPrefHeight(150);

        TableColumn<Course, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setPrefWidth(200);

        TableColumn<Course, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descCol.setPrefWidth(300);

        TableColumn<Course, Integer> capCol = new TableColumn<>("Capacity");
        capCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        capCol.setPrefWidth(100);

        TableColumn<Course, Integer> availableSeatsCol = new TableColumn<>("Available Seats");
        availableSeatsCol.setCellValueFactory(cellData -> {
            Course course = cellData.getValue();

            int availableSeats = course.availableSeats();

            return new javafx.beans.property.SimpleObjectProperty<>(availableSeats);
        });
        availableSeatsCol.setPrefWidth(150);

        availableTableView.getColumns().addAll(titleCol, descCol, capCol, availableSeatsCol);
        availableTableView.getItems().addAll(dashboardController.getAvailableCourses());

        Button enrollButton = new Button("Enroll in Selected Course");
        enrollButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-cursor: hand;");

        TableView<Course> enrolledTableView = new TableView<>();
        enrolledTableView.setPrefHeight(150);

        TableColumn<Course, String> enrTitleCol = new TableColumn<>("Title");
        enrTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        enrTitleCol.setPrefWidth(200);

        TableColumn<Course, String> enrDescCol = new TableColumn<>("Description");
        enrDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        enrDescCol.setPrefWidth(300);

        enrolledTableView.getColumns().addAll(enrTitleCol, enrDescCol);
        enrolledTableView.getItems().addAll(dashboardController.getEnrolledCourses());

        enrollButton.setOnAction(e -> {
            Course selected = availableTableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    dashboardController.enrollCourse(selected.getCourseId());
                    enrolledTableView.getItems().clear();
                    enrolledTableView.getItems().addAll(dashboardController.getEnrolledCourses());
                    showAlert(Alert.AlertType.INFORMATION, "Success",
                            "Enrolled successfully in " + selected.getTitle());
                } catch (Exception ex) {
                    showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
                }
            }
        });

        Label enrolledLabel = new Label("Your Enrolled Courses");
        enrolledLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        content.getChildren().addAll(availableLabel, availableTableView, enrollButton, new Separator(), enrolledLabel,
                enrolledTableView);
    }

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
