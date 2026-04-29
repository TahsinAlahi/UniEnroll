
package unienroll.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Data_input_Controller {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Label label1, label2, label3, label4;

    @FXML
    private TextField studentMail, studentid;
    
    @FXML
    private Button button;
    
    @FXML
    private ImageView image2, image3;
    
    @FXML
    private Label wrong;

    private final unienroll.ui.controllers.AuthController authController;
    private final unienroll.ui.scenes.NavigationHandler navigationHandler;

    public Data_input_Controller(unienroll.ui.controllers.AuthController authController, unienroll.ui.scenes.NavigationHandler navigationHandler) {
        this.authController = authController;
        this.navigationHandler = navigationHandler;
    }

    private int attempts = 0;
    
    
    private boolean idValidated = false;
    
    @FXML
    private void clearFields() {
            studentid.clear();
            studentMail.clear();
        }
    
    /*String email;
    int id;
    public static double cgpa; //so that it is excessible in another places
    double credits;*/

    
    
    @FXML
    private void Register(ActionEvent event) {
        navigationHandler.showRegister();
    }
    
    @FXML
    private void Next(ActionEvent event) throws IOException {
        
        if (attempts >= 3) {
        label4.setText("Too many attempts. Try again later.");
        image3.setImage(new Image(getClass().getResourceAsStream("exclamation.jpg")));
        //button.setDisable(true);
        return;
    }
        
        
        if (studentid.getText().isEmpty() || studentMail.getText().isEmpty()) {

        label4.setText("Please provide all the information first");
        attempts++;
        
        image3.setImage(new Image(getClass().getResourceAsStream("exclamation.jpg")));

        studentid.clear();
        studentid.requestFocus();
        //button.setDisable(true);
        return;
        
    }

    String id = studentid.getText().trim();
    String email = studentMail.getText().trim();

    try {
        // Use our backend AuthController
        unienroll.domain.Member member = authController.login(email, id); // Assuming id is used as password or similar for this UI
        
        label1.setText("Login Successful!");
        
        // Navigation logic: Students start the enrollment flow, others go to Dashboard
        if (member.getRole() == unienroll.domain.Roles.STUDENT) {
            navigationHandler.showStudentInfo();
        } else {
            navigationHandler.showDashboard();
        }

    } catch (Exception e) {
        label4.setText(e.getMessage());
        attempts++;
        image3.setImage(new Image(getClass().getResourceAsStream("warning.png")));
        studentid.clear();
        studentid.requestFocus();
    }
}

    private void loadScene(ActionEvent event, String fxml, String title, String icon) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        // We'll need to pass the factory here. For now, let's assume we set it globally 
        // or we use a helper. Since we're in the controller, we might not have the factory.
        // A better way is to have the MainApp handle navigation.
        
        // To minimize changes, I'll use a static helper for now or just manually set the factory if I can access it.
        loader.setControllerFactory(unienroll.ui.MainApp.getControllerFactory());
        
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.getIcons().clear();
        stage.getIcons().add(new Image(getClass().getResourceAsStream(icon)));
        stage.show();
    }
}