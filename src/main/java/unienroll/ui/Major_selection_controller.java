
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


public class Major_selection_controller implements Initializable {
    
    public static String selectedMajor;
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label major, MAJOR2;

    @FXML
    private ChoiceBox<String> major_choicebox;

    @FXML
    private Button nextButton1;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        String dept = Department_selection_controller.selectedDept;
        
        switch(dept) {
            case "ECE":
            {
                major_choicebox.getItems().addAll("CSE", "EEE");
                break;
            }
            
            case "BMD":
            {
               major_choicebox.getItems().addAll("Micrbiology", "Biotechnology");
               break; 
            }
            
            case "BBA":
            {
                major_choicebox.getItems().addAll("BBA");
                break;
            }
            
            case "Economics":
            {
                major_choicebox.getItems().addAll("Economics");
                break;
            }
            
            case "English":
            {
                major_choicebox.getItems().addAll("English");
                break;
            }
            
            case "Architecture":
            {
                major_choicebox.getItems().addAll("Architecture");
                break;
            }
        }       
          
        major_choicebox.setOnAction(this::getMajor);
        
        nextButton1.setDisable(true);
        
    }

    //changing label
    public void getMajor(ActionEvent event){
       
        selectedMajor = major_choicebox.getValue();
        
        if (selectedMajor == null) 
        {   
        MAJOR2.setText("Please select a major first!");
        //return;
        }
        else{
           MAJOR2.setText("Your major is " +selectedMajor+". Please click on 'Next' to select your courses."); 
        }
        
        nextButton1.setDisable(false);
    }
    
    //clicking on back
    @FXML 
    private void deptScene(ActionEvent event) throws IOException{
           //to make everythinng null: 
           
           Department_selection_controller.selectedDept = null;
           selectedMajor = null;
           
           //Department_selection_controller.nextButton.setDisable(true);
           
    
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
    
    //clicking on next
    
    @FXML
    private void CourseScene(ActionEvent event) throws IOException {

          Parent root4 = FXMLLoader.load(getClass().getResource("Course_selection.fxml"));
          Stage stage4 = (Stage)((Node)event.getSource()).getScene().getWindow();

          Scene scene4 = new Scene(root4);
          stage4.setScene(scene4);
          
          stage4.setWidth(929);   
          stage4.setHeight(667); 
           
         
          stage4.setTitle("Course Selection");
          
          stage4.setResizable(true);
          
          stage4.show();
    }
      
}


