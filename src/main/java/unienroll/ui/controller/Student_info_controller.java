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

public class Student_info_controller {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Label label2, label3, label4, label5, label6, label7;

    @FXML
    private TextField Name, ID, CGPA, Credits;
    
    @FXML
    private Button nextButton, backButton;
    
    @FXML 
    private ImageView warning, logo;
    
    String name;
    public static double cgpa; //so that it is excessible in another places
    double credits;
    String id;

    
    
    @FXML
    private void Next(ActionEvent event) throws IOException {
        
        //nextButton.setDisable(true);
        
        if (Name.getText().isEmpty() ||  CGPA.getText().isEmpty() || Credits.getText().isEmpty() || ID.getText().isEmpty()) {

        warning.setImage(new Image(getClass().getResourceAsStream("warning.png")));
        label7.setText("Please provide all the information first");
        //nextButton.setDisable(true);
        return;
        
    }
        
        //Name Limitations to only alphabets: 
        try {
            
            name = Name.getText();
 
            if (!name.matches("[a-zA-Z ]+")) 
            {
                
              warning.setImage(new Image(getClass().getResourceAsStream("warning.png")));
              label7.setText("Name must contain only letters");
              return;
              
            }
  
        }
        
        catch(Exception e){
            
            warning.setImage(new Image(getClass().getResourceAsStream("warning.png")));
            label7.setText("Name must contain only letters");
            return;
            
        }
        

        //CGPA Range Limitations:
        try {
            
            cgpa = Double.parseDouble(CGPA.getText());  
            credits = Double.parseDouble(Credits.getText());
            id = ID.getText();
 

            

            
           if ((cgpa < 0.0 || cgpa > 4.0) || (credits < 0|| credits > 250) || ((id.length() != 10) || id.matches("[a-zA-Z ]+") )){
                
            warning.setImage(new Image(getClass().getResourceAsStream("warning.png")));
            label7.setText("Invalid Information!");
            return;
            
            }
          

        }
        
        catch(Exception e){
            
            warning.setImage(new Image(getClass().getResourceAsStream("warning.png")));
            label7.setText("Invalid Information!");
            return; //stops the program
        }
        
        nextButton.setDisable(false);
        
           //going to next scene
           
           Parent root2 = FXMLLoader.load(getClass().getResource("/unienroll/ui/fxml/Department_selection.fxml"));
           Stage stage2 = (Stage)((Node)event.getSource()).getScene().getWindow();
           
           Scene scene2= new Scene(root2);
           stage2.setScene(scene2);
           
           stage2.setWidth(929);   
           stage2.setHeight(700); 
           
           stage2.setTitle("Department Selection");
           
           stage2.getIcons().clear(); //clearing previous image
           stage2.getIcons().add(new Image(getClass().getResourceAsStream("department_icon.png")));
           
           stage2.setResizable(false);
           
           stage2.show();
           
           
    }
    
    //Going back 
    @FXML
    private void Back (ActionEvent event) throws IOException {
        
           Parent root1 = FXMLLoader.load(getClass().getResource("/unienroll/ui/fxml/Data_input.fxml"));
           Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
           
           Scene scene1 = new Scene(root1);
           stage.setScene(scene1);
        
           stage.setWidth(929);   
           stage.setHeight(700); 
           
           stage.setTitle("Student Login");
           
           stage.getIcons().add(new Image(getClass().getResourceAsStream("Human_icon.jpg")));
           
           stage.setResizable(false);
           
                  
           stage.setScene(scene1);
           stage.show();
    }
}