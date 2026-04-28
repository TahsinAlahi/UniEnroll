package unienroll.ui.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import unienroll.ui.controllers.AuthController;

public class LoginView {
    private final AuthController authController;
    private final NavigationHandler navigationHandler;

    public LoginView(AuthController authController, NavigationHandler navigationHandler) {
        this.authController = authController;
        this.navigationHandler = navigationHandler;
    }

    public Scene getScene() {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #f4f4f4; -fx-font-family: 'Inter', sans-serif;");

        Label title = new Label("UniEnroll Login");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(300);
        emailField.setStyle("-fx-padding: 10px; -fx-font-size: 14px; -fx-background-radius: 5px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);
        passwordField.setStyle("-fx-padding: 10px; -fx-font-size: 14px; -fx-background-radius: 5px;");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
        errorLabel.setVisible(false);

        Button loginButton = new Button("Login");
        loginButton.setMaxWidth(300);
        loginButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5px; -fx-cursor: hand;");
        
        loginButton.setOnAction(e -> {
            try {
                authController.login(emailField.getText(), passwordField.getText());
                navigationHandler.showDashboard();
            } catch (Exception ex) {
                errorLabel.setText(ex.getMessage());
                errorLabel.setVisible(true);
            }
        });

        Hyperlink registerLink = new Hyperlink("Don't have an account? Register");
        registerLink.setStyle("-fx-text-fill: #3498db;");
        registerLink.setOnAction(e -> navigationHandler.showRegister());

        root.getChildren().addAll(title, emailField, passwordField, loginButton, errorLabel, registerLink);

        return new Scene(root, 800, 600);
    }
}
