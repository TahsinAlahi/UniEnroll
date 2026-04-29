package unienroll.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
   
    private int currentCredits = 0; //*
    
    private boolean isUpdating = false; //*

    private final unienroll.ui.controllers.CourseController courseController;
    private final unienroll.ui.SessionState sessionState;

    public Course_selection_controller(unienroll.ui.controllers.CourseController courseController) {
        this.courseController = courseController;
        this.sessionState = unienroll.ui.SessionState.getInstance();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
        feeColumn.setCellValueFactory(new PropertyValueFactory<>("fee"));
        
        selectColumn.setCellFactory(col -> new javafx.scene.control.cell.CheckBoxTableCell<>()); //*
        selectColumn.setCellValueFactory(cellData -> cellData.getValue().selectProperty()); //*
        
        coreTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //to allow multiple selection
        
        //setting credit and course limit: 
        cgpa = courseController.getStudentCgpa();
        
        if(cgpa>= 3.85){
            maxCredits = 18;
            maxCourses = 10;
           }
        else if (cgpa >= 3.50) {
            maxCredits = 16;
            maxCourses = 8;
        }
        else if (cgpa >= 3.00) {
            maxCredits = 14;
            maxCourses = 6;
        }
        else if (cgpa >= 2.00) {
            maxCredits = 12;
            maxCourses = 5;
        }
        else {
            maxCredits = 10;
            maxCourses = 4;
        }
        
        declaration.setText("Based on your CGPA of " + cgpa + ", you may take up to "+ maxCredits + " credits and "+ maxCourses + " courses.");
        
        // Fetch courses from backend via controller
        String major = sessionState.getSelectedMajor();
        ObservableList<Course> courses = courseController.getCoursesForMajor(major);

        coreTable.setItems(courses);
        
        for (Course c : courses) {  //*

            c.selectProperty().addListener((obs, oldVal, newVal) -> {

                if (isUpdating) return; 

                isUpdating = true;

                if (newVal) {
                    if (currentCredits + c.getCredits() > maxCredits) {
                        c.setSelected(false); 
                        declaration.setText("Credit limit exceeded! Max: " + maxCredits);
                    } else {
                        currentCredits += (int)c.getCredits();
                        declaration.setText("Selected Credits: " + currentCredits + "/" + maxCredits);
                    }

                } else {
                    currentCredits -= (int)c.getCredits();
                    declaration.setText("Selected Credits: " + currentCredits + "/" + maxCredits);
                }

                isUpdating = false;  //*
            });
        }
    }

    @FXML
    private void confirmSelection() {
        ObservableList<Course> selectedCourses = FXCollections.observableArrayList();
        for (Course c : coreTable.getItems()) {
            if (c.isSelected()) {
                selectedCourses.add(c);
            }
        }

        if (selectedCourses.isEmpty()) {
            declaration.setText("❌ Please select at least one course!");
            return;
        }

        if (selectedCourses.size() > maxCourses) {
            declaration.setText("❌ You cannot select more than " + maxCourses + " courses!");
            return;
        }

        try {
            courseController.enrollInCourses(selectedCourses);
            declaration.setText("✅ Courses confirmed successfully!");
            declaration.setStyle("-fx-text-fill: green;");
        } catch (Exception e) {
            declaration.setText("❌ Enrollment failed: " + e.getMessage());
            declaration.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        loadScene(event, "Major_selection.fxml", "Major Selection", "major.png");
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
