
package unienroll.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ReceiptController {

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
}