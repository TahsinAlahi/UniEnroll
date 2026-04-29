package unienroll.ui;
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
import javafx.stage.Stage;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import unienroll.domain.Student;
import unienroll.domain.Member;

public class Student_info_controller implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Label label2, label3, label4, label5, label6;

    @FXML
    private TextField Name, ID, CGPA, Credits;
    
    @FXML
    private Button nextButton, backButton;
    
    String name;
    String id;
    public static double cgpa; 
    double credits;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Member user = UserSession.getInstance().getLoggedInUser();
        if (user != null) {
            Name.setText(user.getName());
            ID.setText(user.getId());
            ID.setEditable(false); // ID should not be changed by user

            if (user instanceof Student) {
                Student student = (Student) user;
                CGPA.setText(String.valueOf(student.getCgpa()));
                // Credits could be calculated or stored
            }
        }
    }
    
    @FXML
    private void Next(ActionEvent event) throws IOException {
        
        if (Name.getText().isEmpty() || ID.getText().isEmpty() || CGPA.getText().isEmpty() || Credits.getText().isEmpty()) {
            label6.setText("Please provide all the information first");
            return;
        }

        name = Name.getText();
        
        try {
            cgpa = Double.parseDouble(CGPA.getText());
            credits = Double.parseDouble(Credits.getText());
            
            // Sync with backend
            Member user = UserSession.getInstance().getLoggedInUser();
            if (user != null) {
                user.setName(name);
                if (user instanceof Student) {
                    ((Student) user).setCgpa(cgpa);
                }
                ServiceLocator.getInstance().getMemberService().updateMember(user);
            }
        } catch(Exception e){
            label6.setText("Enter a valid CGPA or Credits");
            return; 
        }
        
        nextButton.setDisable(false);
        
        Parent root2 = FXMLLoader.load(getClass().getResource("Department_selection.fxml"));
        Stage stage2 = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        Scene scene2= new Scene(root2);
        stage2.setScene(scene2);
        
        stage2.setWidth(600);   
        stage2.setHeight(450); 
        stage2.setTitle("Department Selection");
        stage2.getIcons().clear();
        stage2.getIcons().add(new Image(getClass().getResourceAsStream("department_icon.png")));
        stage2.setResizable(true);
        stage2.show();
    }
    
    //Going back 
    @FXML
    private void Back (ActionEvent event) throws IOException {
           Parent root1 = FXMLLoader.load(getClass().getResource("Data_input.fxml"));
           Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
           
           Scene scene1 = new Scene(root1);
           stage.setScene(scene1);
        
           stage.setWidth(800);   
           stage.setHeight(450); 
           
           stage.setTitle("Student Login");
           
           stage.getIcons().add(new Image(getClass().getResourceAsStream("Human_icon.jpg")));
           
           stage.setResizable(true);
           
                  
           stage.setScene(scene1);
           stage.show();
    }
}