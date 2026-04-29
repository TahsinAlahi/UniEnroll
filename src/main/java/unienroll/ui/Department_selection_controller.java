
package unienroll.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Department_selection_controller implements Initializable{
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label department, dept;

    @FXML
    private ChoiceBox<String> dept_choicebox;
    
    @FXML
    private Button nextButton;

    private String[] departments = {"ECE", "BMD", "BBA", "Architecture"};
    
    public static String selectedDept;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        nextButton.setDisable(true);
        
        dept_choicebox.getItems().addAll(departments);
        
        dept_choicebox.setOnAction(this::getDepartments); //:: method ref operator
        
    }

    //changing label
    public void getDepartments(ActionEvent event){
         
        selectedDept = dept_choicebox.getValue();
        
        if (selectedDept == null) 
        {   
        dept.setText("Please select a department first!");
        //return;
        }
        else{
           dept.setText("Your department is " +selectedDept+". Please click on 'Next' to select your major"); 
           nextButton.setDisable(false);
        }
          
    }
    
     //next event
    @FXML
    private void MajorScene(ActionEvent event) throws IOException {
           
           /*if (selectedDept == null || dept_choicebox.getValue() == null){
               
           dept.setText("Please select a department first!");
           nextButton.setDisable(true);
           return;
           
            }*/
           
           Parent root3 = FXMLLoader.load(getClass().getResource("Major_selection.fxml"));
           Stage stage3 = (Stage)((Node)event.getSource()).getScene().getWindow();
           
           Scene scene3= new Scene(root3);
           stage3.setScene(scene3);
           
           stage3.setWidth(600);   
           stage3.setHeight(450); 
           
           stage3.setTitle("Major Selection");
           
           stage3.getIcons().clear();
           stage3.getIcons().add(new Image(getClass().getResourceAsStream("major.png")));
           
           stage3.setResizable(true);
           
           stage3.show();
            
        }
    
    //going back event
    @FXML
    private void StudentInfoScene(ActionEvent event) throws IOException {
           
           Parent root6 = FXMLLoader.load(getClass().getResource("Student_info.fxml"));
           Stage stage6 = (Stage)((Node)event.getSource()).getScene().getWindow();
           
           Scene scene6= new Scene(root6);
           stage6.setScene(scene6);
           
           stage6.setWidth(600);   
           stage6.setHeight(450); 
           
           stage6.setTitle("Student Information");
           
           stage6.getIcons().clear(); //clearing previous image
           stage6.getIcons().add(new Image(getClass().getResourceAsStream("department_icon.png")));
           
           stage6.setResizable(true);
           
           stage6.show();
           
          
    }
    
}
