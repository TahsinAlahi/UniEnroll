
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

    private final unienroll.ui.SessionState sessionState;

    public Department_selection_controller() {
        this.sessionState = unienroll.ui.SessionState.getInstance();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        nextButton.setDisable(true);
        
        unienroll.domain.Member member = sessionState.getLoggedInMember();
        if (member != null && !member.getIsVerified()) {
            dept.setText("❌ You are not verified by an admin yet. Please wait for verification.");
            dept.setStyle("-fx-text-fill: red;");
            dept_choicebox.setDisable(true);
            return;
        }

        dept_choicebox.getItems().addAll(departments);
        
        dept_choicebox.setOnAction(this::getDepartments); //:: method ref operator
        
    }

    //changing label
    public void getDepartments(ActionEvent event){
         
        selectedDept = dept_choicebox.getValue();
        sessionState.setSelectedDepartment(selectedDept);
        
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
        loadScene(event, "Major_selection.fxml", "Major Selection", "major.png");
    }
    
    //going back event
    @FXML
    private void StudentInfoScene(ActionEvent event) throws IOException {
        loadScene(event, "Student_info.fxml", "Student Information", "department_icon.png");
    }

    private void loadScene(ActionEvent event, String fxml, String title, String icon) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.setControllerFactory(unienroll.ui.MainApp.getControllerFactory());
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(600);
        stage.setHeight(450);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.getIcons().clear();
        if (icon != null) stage.getIcons().add(new Image(getClass().getResourceAsStream(icon)));
        stage.show();
    }
    
}
