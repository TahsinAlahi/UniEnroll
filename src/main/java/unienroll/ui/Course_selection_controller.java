
package unienroll.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import unienroll.application.CourseService;
import unienroll.domain.Member;
import unienroll.domain.Student;
import unienroll.ui.ServiceLocator;
import unienroll.ui.UserSession;


public class Course_selection_controller implements Initializable {
    
    private int maxCredits;
    private int maxCourses;
    double cgpa;

    @FXML
    private TableView<Course> coreTable;

    @FXML
    private TableColumn<Course, String> codeColumn;

    @FXML
    private TableColumn<Course, String> titleColumn;
    
    @FXML
    private TableColumn<Course, Integer> creditColumn;

    @FXML
    private TableColumn<Course, Double> feeColumn;
    
    @FXML
    private Label declaration;
    
    @FXML
    private TableColumn<Course, Boolean> selectColumn; 
   
    private double currentCredits = 0; 
    
    private boolean isUpdating = false; 
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
        feeColumn.setCellValueFactory(new PropertyValueFactory<>("fee"));
        
        selectColumn.setCellFactory(col -> new javafx.scene.control.cell.CheckBoxTableCell<>()); 
        selectColumn.setCellValueFactory(cellData -> cellData.getValue().selectProperty()); 
        
        coreTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); 
        
        cgpa = Student_info_controller.cgpa;
        
        if(cgpa>= 3.85){
            maxCredits = 18;
            maxCourses = 10;
        } else if (cgpa >= 3.50) {
            maxCredits = 16;
            maxCourses = 8;
        } else if (cgpa >= 3.00) {
            maxCredits = 14;
            maxCourses = 6;
        } else if (cgpa >= 2.00) {
            maxCredits = 12;
            maxCourses = 5;
        } else {
            maxCredits = 10;
            maxCourses = 4;
        }
        
        declaration.setText("Based on your CGPA of " + cgpa + ", you may take up to "+ maxCredits + " credits and "+ maxCourses + " courses.");
        
        ObservableList<Course> uiCourses = FXCollections.observableArrayList(); 

        // LOAD FROM BACKEND
        CourseService courseService = ServiceLocator.getInstance().getCourseService();
        List<unienroll.domain.Course> allBackendCourses = courseService.listAllCourses();
        
        for (unienroll.domain.Course backendCourse : allBackendCourses) {
            String fullTitle = backendCourse.getTitle();
            String code = fullTitle;
            String title = fullTitle;
            
            // Try to split "CSE101 Intro to CS" into Code="CSE101" and Title="Intro to CS"
            if (fullTitle.contains(" ")) {
                int firstSpace = fullTitle.indexOf(" ");
                String firstPart = fullTitle.substring(0, firstSpace);
                if (firstPart.length() <= 8) { // Likely a code
                    code = firstPart;
                    title = fullTitle.substring(firstSpace + 1);
                }
            }

            uiCourses.add(new Course(
                backendCourse.getCourseId(), 
                code, 
                title, 
                backendCourse.getCredits(), 
                backendCourse.getFee(), 
                false
            ));
        }

        coreTable.setItems(uiCourses);
        
        for (Course c : uiCourses) {  
            c.selectProperty().addListener((obs, oldVal, newVal) -> {
                if (isUpdating) return; 
                isUpdating = true;

                if (newVal) {
                    if (currentCredits + c.getCredits() > maxCredits) {
                        c.setSelected(false); 
                        declaration.setText("Credit limit exceeded!");
                    } else {
                        currentCredits += c.getCredits();
                        declaration.setText("Selected Credits: " + currentCredits + "/" + maxCredits);
                    }
                } else {
                    currentCredits -= c.getCredits();
                    declaration.setText("Selected Credits: " + currentCredits + "/" + maxCredits);
                }
                isUpdating = false;  
            });
        }
    }   

    @FXML
    private void confirmSelection(ActionEvent event) throws IOException {
        ObservableList<Course> allCourses = coreTable.getItems();
        ObservableList<Course> selectedCourses = FXCollections.observableArrayList();
        for(Course c : allCourses) {
            if(c.isSelected()) {
                selectedCourses.add(c);
            }
        }

        if (selectedCourses.isEmpty()) {
            declaration.setText("Please select at least one course!");
            return;
        }

        if (selectedCourses.size() > maxCourses) {
            declaration.setText("You cannot select more than " + maxCourses + " courses!");
            return;
        }

        // PERFORM ENROLLMENT
        Member user = UserSession.getInstance().getLoggedInUser();
        if (user instanceof Student) {
            CourseService courseService = ServiceLocator.getInstance().getCourseService();
            try {
                for (Course c : selectedCourses) {
                    courseService.enrollStudent(user.getId(), c.getId()); // Use UUID here
                }
                
                // Navigate to Receipt
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Receipt.fxml"));
                Parent root = loader.load();
                ReceiptController controller = loader.getController();
                controller.setData(selectedCourses);

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Registration Receipt");
                stage.show();

            } catch (Exception e) {
                declaration.setText("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void Back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Major_selection.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Major Selection");
        stage.show();
    }
}
