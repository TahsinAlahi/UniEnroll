package unienroll.ui.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import unienroll.ui.controllers.AuthController;

public class RegisterView {
    private final AuthController authController;
    private final NavigationHandler navigationHandler;

    public RegisterView(AuthController authController, NavigationHandler navigationHandler) {
        this.authController = authController;
        this.navigationHandler = navigationHandler;
    }

    public Scene getScene() {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #f4f4f4; -fx-font-family: 'Inter', sans-serif;");

        Label title = new Label("Register for UniEnroll");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");
        nameField.setMaxWidth(300);
        nameField.setStyle("-fx-padding: 10px; -fx-font-size: 14px; -fx-background-radius: 5px;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(300);
        emailField.setStyle("-fx-padding: 10px; -fx-font-size: 14px; -fx-background-radius: 5px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);
        passwordField.setStyle("-fx-padding: 10px; -fx-font-size: 14px; -fx-background-radius: 5px;");

        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("Student", "Admin");
        roleCombo.setValue("Student");
        roleCombo.setMaxWidth(300);
        roleCombo.setStyle("-fx-padding: 5px; -fx-font-size: 14px;");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 12px;");
        messageLabel.setVisible(false);
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(300);

        Button registerButton = new Button("Register");
        registerButton.setMaxWidth(300);
        registerButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5px; -fx-cursor: hand;");

        registerButton.setOnAction(e -> {
            try {
                // TODO: NSU email verification
                if (!emailField.getText().endsWith("@northsouth.edu")) {
                    throw new Exception("Please use NSU email to register.");
                }
                if ("Student".equals(roleCombo.getValue())) {
                    authController.registerStudent(nameField.getText(), emailField.getText(), passwordField.getText());
                } else {
                    authController.registerAdmin(nameField.getText(), emailField.getText(), passwordField.getText());
                }
                messageLabel.setText("Registration successful! Please login.");
                messageLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 12px;");
                messageLabel.setVisible(true);
            } catch (Exception ex) {
                messageLabel.setText(ex.getMessage());
                messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
                messageLabel.setVisible(true);
            }
        });

        Hyperlink loginLink = new Hyperlink("Already have an account? Login");
        loginLink.setStyle("-fx-text-fill: #3498db;");
        loginLink.setOnAction(e -> navigationHandler.showLogin());

        root.getChildren().addAll(title, nameField, emailField, passwordField, roleCombo, registerButton, messageLabel, loginLink);

        return new Scene(root, 800, 600);
    }
}
