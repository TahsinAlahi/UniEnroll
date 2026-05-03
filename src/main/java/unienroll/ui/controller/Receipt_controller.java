
package unienroll.ui.controller;


import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Receipt_controller {

    @FXML
    private TextArea receiptArea;

    @FXML
    private Label totalCreditsLabel;

    @FXML
    private Label totalFeeLabel;
    
    

    public void setData(ObservableList<Course> courses) {

    double totalCredits = 0;
    double totalFee = 0;

    StringBuilder receipt = new StringBuilder();

    for (Course c : courses) {
        receipt.append(c.getCode())
               .append(" - ")
               .append(c.getTitle())
               .append(" | Credits: ")
               .append(c.getCredits())
               .append(" | Fee: ")
               .append(c.getFee())
               .append("\n");

        totalCredits += c.getCredits();
        totalFee += c.getFee();
    }

    
    receiptArea.setText(receipt.toString());

    totalCreditsLabel.setText("Total Credits: " + totalCredits);
    totalFeeLabel.setText("Total Fee: " + totalFee);
    }
    
    //closing program
    @FXML
    private void close(ActionEvent event) throws IOException {

          
          Stage stage9 = (Stage)((Node)event.getSource()).getScene().getWindow();

          stage9.close();
    }
    
    //going back
    @FXML
    private void back(ActionEvent event) throws IOException{
       

          Parent root4 = FXMLLoader.load(getClass().getResource("/unienroll/ui/fxml/Course_selection.fxml"));
          Stage stage4 = (Stage)((Node)event.getSource()).getScene().getWindow();

          Scene scene4 = new Scene(root4);
          stage4.setScene(scene4);
          
          stage4.setWidth(929);   
          stage4.setHeight(700); 
           
         
          stage4.setTitle("Course Selection");
          
          stage4.setResizable(false);
          
          stage4.show();
    }
    

}

