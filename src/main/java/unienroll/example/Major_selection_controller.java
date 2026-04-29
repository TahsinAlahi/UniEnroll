
package unienroll.example;

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
    
    private final unienroll.ui.SessionState sessionState;

    public Major_selection_controller() {
        this.sessionState = unienroll.ui.SessionState.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        String dept = sessionState.getSelectedDepartment();
        if (dept == null) dept = ""; // fallback
        
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
        sessionState.setSelectedMajor(selectedMajor);
        
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
           
           sessionState.setSelectedDepartment(null);
           sessionState.setSelectedMajor(null);
           
           loadScene(event, "Department_selection.fxml", "Department Selection", "department_icon.png");
    }
    
    //clicking on next
    
    @FXML
    private void CourseScene(ActionEvent event) throws IOException {
        loadScene(event, "Course_selection.fxml", "Course Selection", null);
    }

    private void loadScene(ActionEvent event, String fxml, String title, String icon) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.setControllerFactory(unienroll.ui.MainApp.getControllerFactory());
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(title);
        // Custom dimensions for Course Selection as per original code
        if (fxml.equals("Course_selection.fxml")) {
            stage.setWidth(929);
            stage.setHeight(667);
        } else {
            stage.setWidth(600);
            stage.setHeight(450);
        }
        stage.setResizable(false);
        stage.getIcons().clear();
        if (icon != null) stage.getIcons().add(new Image(getClass().getResourceAsStream(icon)));
        stage.show();
    }
}


