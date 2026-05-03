
package unienroll.ui.controller;

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
import unienroll.application.MemberService;

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
    private void Next(ActionEvent event) throws IOException {
        
        if (attempts >= 5) {
            label4.setText("Too many attempts. Contact admin.");
            image3.setImage(new Image(getClass().getResourceAsStream("exclamation.jpg")));
            return;
        }
        
        if (studentid.getText().isEmpty() || studentMail.getText().isEmpty()) {
            label4.setText("Please provide both Email and Password");
            attempts++;
            image3.setImage(new Image(getClass().getResourceAsStream("exclamation.jpg")));
            return;
        }

        String password = studentid.getText().trim();
        String email = studentMail.getText().trim();

        try {
            MemberService memberService = ServiceLocator.getInstance().getMemberService();
            unienroll.domain.Member member = memberService.login(email, password);

            if (!member.getIsVerified()) {
                label4.setText("Account not verified yet.");
                image3.setImage(new Image(getClass().getResourceAsStream("warning.png")));
                return;
            }

            // Success
            UserSession.getInstance().login(member);
            label1.setText("Login Successful!");

            // Navigate to next scene
            Parent root6 = FXMLLoader.load(getClass().getResource("/unienroll/ui/fxml/Student_info.fxml"));
            Stage stage6 = (Stage)((Node)event.getSource()).getScene().getWindow();
            
            Scene scene6= new Scene(root6);
            stage6.setScene(scene6);
            
            stage6.setWidth(929);   
            stage6.setHeight(700); 
            stage6.setTitle("Student Information");
            stage6.getIcons().clear();
            stage6.getIcons().add(new Image(getClass().getResourceAsStream("department_icon.png")));
            stage6.setResizable(true);
            stage6.show();

        } catch (unienroll.exception.NotFoundException | unienroll.exception.UnauthorizedException e) {
            label4.setText(e.getMessage());
            image3.setImage(new Image(getClass().getResourceAsStream("warning.png")));
            attempts++;
        } catch (Exception e) {
            label4.setText("System error. Try again.");
            e.printStackTrace();
        }
    }
}