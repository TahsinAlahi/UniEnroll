
package unienroll.ui.controller;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;


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
    private TableColumn<Course, Double> creditColumn;

    @FXML
    private TableColumn<Course, Double> feeColumn;
    
    @FXML
    private Label declaration;
    
    @FXML
    private TableColumn<Course, Boolean> selectColumn;
    
    @FXML
    private Label totalCreditsLabel;

    @FXML
    private Label totalFeeLabel;
    
    @FXML
    private Label warningLabel, warningLabel2;

    private double currentCredits = 0.0; //
    private boolean isUpdating = false;  //
   
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
        feeColumn.setCellValueFactory(new PropertyValueFactory<>("fee"));
        
        selectColumn.setCellValueFactory(cellData -> cellData.getValue().selectProperty()); //*
        
        selectColumn.setCellFactory(col -> new javafx.scene.control.cell.CheckBoxTableCell<>()); //*
        
        
        coreTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //to allow multiple selection
        
        //setting credit and course limit: 
        cgpa = Student_info_controller.cgpa;
        
        
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
        
        
        double cgpa_set = Student_info_controller.cgpa;
            
        declaration.setText("Based on your CGPA of " + cgpa_set + ", you may take up to "+ maxCredits + " credits and "+ maxCourses + " courses.");
        
        ObservableList<Course> courses = FXCollections.observableArrayList(); //in order to get/view courses based on selected major

        switch(Major_selection_controller.selectedMajor) {

            case "CSE":
            {  
                courses.add(new Course("CSE115", "Programming Language I", 3, 19500, false));
                courses.add(new Course("CSE115L", "Programming Language I Lab", 1, 6000,false));
                courses.add(new Course("CSE173", "Discrete Mathematics", 3.0, 19500, false));
                courses.add(new Course("CSE215", "Programming Language II", 3.0, 19500, false));
                courses.add(new Course("CSE215L", "Programming Language II Lab", 1.0, 6500, false));
                courses.add(new Course("CSE225", "Data Structures and Algorithms", 3.0, 19500, false));
                courses.add(new Course("CSE225L", "Data Structures and Algorithms Lab", 1.0, 6500, false));
                courses.add(new Course("CSE231", "Digital Logic Design", 3.0, 19500, false));
                courses.add(new Course("CSE231L", "Digital Logic Design Lab", 1.0, 6500, false));
                courses.add(new Course("CSE273", "Introduction to Theory of Computation", 3.0, 19500,false));
                courses.add(new Course("CSE299", "Junior Design", 1.0, 6500,false));
                courses.add(new Course("CSE311", "Database Management Systems", 3.0, 19500,false));
                courses.add(new Course("CSE323", "Operating System Design", 3.0, 19500,false));
                courses.add(new Course("CSE325", "Programming Languages Principles", 3.0, 19500,false));
                courses.add(new Course("CSE327", "Software Engineering", 3.0, 19500,false));
                courses.add(new Course("CSE331", "Microprocessor Interfacing & Embedded System", 3.0, 19500,false));
                courses.add(new Course("CSE331L", "Microprocessor Interfacing & Embedded System Lab", 1.0, 6500,false));
                courses.add(new Course("CSE332", "Computer Organization and Architecture", 3.0, 19500,false));
                courses.add(new Course("CSE373", "Design and Analysis of Algorithms", 3.0, 19500,false));
                courses.add(new Course("CSE411", "Advanced Database Management System", 3.0, 19500,false));
                courses.add(new Course("CSE413", "Verilog HDL: Modeling, Simulation and Synthesis", 3.0, 19500,false));
                courses.add(new Course("CSE413L", "Verilog HDL: Modeling, Simulation and Synthesis Lab", 3.0, 6500,false));
                courses.add(new Course("CSE422", "Modeling and Simulation", 3.0, 19500,false));
                courses.add(new Course("CSE433", "Computer Architecture", 3.0, 19500,false));
                courses.add(new Course("CSE435", "Introduction to VLSI Design", 3.0, 19500,false));
                courses.add(new Course("CSE435L", "Introduction to VLSI Design Lab", 3.0, 6500,false));
                courses.add(new Course("CSE440", "Artificial Intelligence", 3.0, 19500,false));
                courses.add(new Course("CSE445", "Machine Learning", 3.0, 19500,false));
                courses.add(new Course("CSE465", "Pattern Recognition", 3.0, 19500,false));
                courses.add(new Course("CSE468", "Computer Vision", 3.0, 19500,false));
                courses.add(new Course("CSE482", "Internet and Web Technology", 3.0, 19500,false));
                courses.add(new Course("CSE482L", "Internet and Web Technology Lab", 3.0, 6500,false));
                courses.add(new Course("CSE495A", "Special Topics", 3.0, 19500,false));
                courses.add(new Course("CSE495B", "Special Topics - Natural Language Processing", 3.0, 19500,false));
                courses.add(new Course("CSE495A", "Special Topics", 3.0, 19500,false));
                courses.add(new Course("CSE498R", "Intern/Co-op/Directed Research", 1.0, 6500,false));
                courses.add(new Course("CSE499A", "Senior Design Project I", 1.5, 9750,false));
                courses.add(new Course("CSE499B", "Senior Design Project II", 1.5, 9750,false));
                courses.add(new Course("CSE511", "Advanced Algorithms", 3.0, 19500,false));
                courses.add(new Course("CSE512", "Distributed Database Systems", 3.0, 19500,false));
                courses.add(new Course("CSE533", "Machine Learning", 3.0, 19500,false));
                courses.add(new Course("BIO103", "Biology I", 3.0, 19500, false));
                courses.add(new Course("BIO103L", "Biology I Lab", 3.0, 19500, false));
                courses.add(new Course("CHE101", "General Chemistry", 3.0, 19500, false));
                courses.add(new Course("CHE101L", "General Chemistry Lab", 1.0, 6500, false));
                courses.add(new Course("ENG102", "Introduction to Composition", 3.0, 19500, false));
                courses.add(new Course("ENG103", "Intermediate Composition", 3.0, 19500, false));
                courses.add(new Course("ENG111", "Public Speaking", 3.0, 19500, false));
                courses.add(new Course("ENG115", "Introduction to Literature", 3.0, 19500, false));
                courses.add(new Course("MAT116", "Pre-Calculus", 3.0, 19500, false));
                courses.add(new Course("MAT120", "Calculus and Analytical Geometry I", 3.0, 19500, false));
                courses.add(new Course("MAT125", "Introduction to Linear Algebra", 3.0, 19500, false));
                courses.add(new Course("MAT130", "Calculus and Analytical Geometry II", 3.0, 19500, false));
                courses.add(new Course("MAT250", "Calculus and Analytical Geometry IV", 3.0, 19500, false));
                courses.add(new Course("MAT350", "Engineering Mathematics", 3.0, 19500, false));
                courses.add(new Course("MAT361", "Probability and Statistics", 3.0, 19500, false));
                courses.add(new Course("MAT481", "Numerical Methods", 3.0, 19500, false));
                courses.add(new Course("PHI104", "Introduction to Ethics", 3.0, 19500, false));
                courses.add(new Course("PHY107", "Physics I", 3.0, 19500, false));
                courses.add(new Course("PHY107L", "Physics I Lab", 1.0, 6500, false));
                courses.add(new Course("PHY108", "Physics II", 3.0, 19500, false));
                courses.add(new Course("PHY108L", "Physics II Lab", 1.0, 6500, false));
                courses.add(new Course("POL101", "Introduction to Political Science", 3.0, 19500, false));
                courses.add(new Course("POL104", "Introduction to Governance", 3.0, 19500, false));
                
                break;
                
                //courses.add(new Course());

            }

            case "Micrbiology":
            {
               courses.add(new Course("CHE101", "General Chemistry", 3.0, 19500, false));
               courses.add(new Course("CHE101L", "General Chemistry Lab", 1.0, 6500, false));
               courses.add(new Course("CHE201", "Biophysical Chemistry", 3.0, 19500, false));
               courses.add(new Course("CHE202", "Bio-Organic Chemistry", 3.0, 19500, false));
               courses.add(new Course("CHE202L", "Bio-Organic Chemistry Lab", 1.0, 6500, false));
               courses.add(new Course("MIC201", "Microbial Ecology", 3.0, 19500, false));
               courses.add(new Course("MIC202", "Basic Techniques in Microbiology", 3.0, 19500, false));
               courses.add(new Course("MIC206", "Microbial Taxonomy & Diversity", 3.0, 19500, false));
               courses.add(new Course("MIC307", "Microbial Genetics", 3.0, 19500, false));
               courses.add(new Course("MIC314", "Environmental Microbiology & Bioremediation", 3.0, 19500, false));
               courses.add(new Course("MIC316", "Molecular Biology", 3.0, 19500, false));
               courses.add(new Course("MIC316L", "Molecular Biology Lab", 1.0, 6500, false));
               courses.add(new Course("MIC401", "Microbial Biotechnology", 3.0, 19500, false));
               courses.add(new Course("MIC412", "Bacterial Pathogenesis", 3.0, 19500, false)); 
               courses.add(new Course("ENG102", "Introduction to Composition", 3.0, 19500, false));
               courses.add(new Course("ENG103", "Intermediate Composition", 3.0, 19500, false));
               courses.add(new Course("ENG111", "Public Speaking", 3.0, 19500, false));
               courses.add(new Course("ENG115", "Introduction to Literature", 3.0, 19500, false));
               courses.add(new Course("MAT116", "Pre-Calculus", 3.0, 19500, false));
               courses.add(new Course("POL101", "Introduction to Political Science", 3.0, 19500, false));
               courses.add(new Course("POL104", "Introduction to Governance", 3.0, 19500, false));

                break;
            }
            
            case "Biotechnology":
            {
                courses.add(new Course("BBT221", "Human Physiology", 3.0, 19500, false));
                courses.add(new Course("BBT230", "Biostatistics", 3.0, 19500, false));
                courses.add(new Course("BBT312", "Molecular Biology", 3.0, 19500, false));
                courses.add(new Course("BBT312L", "Molecular Biology Lab", 1.0, 6500, false));
                courses.add(new Course("BBT315", "Metabolism", 3.0, 19500, false));
                courses.add(new Course("BBT316", "Immunology", 3.0, 19500, false));
                courses.add(new Course("BBT317", "Molecular Genetics", 3.0, 19500, false));
                courses.add(new Course("BBT413", "Plant Biochemistry & Biotechnology", 3.0, 19500, false));
                courses.add(new Course("BBT413L", "Plant Biochemistry & Biotechnology Lab", 1.0, 6500, false));
                courses.add(new Course("BBT415", "Molecular Biotechnology", 3.0, 19500, false));
                courses.add(new Course("BBT415L", "Molecular Biotechnology Lab", 1.0, 6500, false));
                courses.add(new Course("BBT416", "Bioinformatics", 3.0, 19500, false));
                courses.add(new Course("CHE101", "General Chemistry", 3.0, 19500, false));
                courses.add(new Course("CHE101L", "General Chemistry Lab", 1.0, 6500, false));
                courses.add(new Course("CHE201", "Biophysical Chemistry", 3.0, 19500, false));
                courses.add(new Course("CHE202", "Bio-Organic Chemistry", 3.0, 19500, false));
                courses.add(new Course("CHE202L", "Bio-Organic Chemistry Lab", 1.0, 6500, false));
                courses.add(new Course("ENG102", "Introduction to Composition", 3.0, 19500, false));
                courses.add(new Course("ENG103", "Intermediate Composition", 3.0, 19500, false));
                courses.add(new Course("ENG111", "Public Speaking", 3.0, 19500, false));
                courses.add(new Course("ENG115", "Introduction to Literature", 3.0, 19500, false));
                courses.add(new Course("MAT116", "Pre-Calculus", 3.0, 19500, false));
                courses.add(new Course("POL101", "Introduction to Political Science", 3.0, 19500, false));
                courses.add(new Course("POL104", "Introduction to Governance", 3.0, 19500, false));
                
                break;
            }
            
            

            case "EEE":
            {  
                courses.add(new Course("EEE111", "Analog Electronics I", 3.0, 19500, false));
                courses.add(new Course("EEE111L", "Analog Electronics I Lab", 1.0, 6500, false));
                courses.add(new Course("EEE141", "Electrical Circuits I", 3.0, 19500, false));
                courses.add(new Course("EEE141L", "Electrical Circuits I Lab", 1.0, 6500, false));
                courses.add(new Course("EEE211", "Digital Electronics", 3.0, 19500, false));
                courses.add(new Course("EEE299", "Junior Design", 1.0, 6500, false));
                courses.add(new Course("EEE311", "Analog Electronics II", 3.0, 19500, false));
                courses.add(new Course("EEE311L", "Analog Electronics II Lab", 1.0, 6500, false));
                courses.add(new Course("EEE361", "Theory of Electromagnetic Field", 3.0, 19500, false));
                courses.add(new Course("EEE452", "Engineering Economics and Management", 3.0, 19500, false));
                courses.add(new Course("EEE498R", "Intern/Co-op/Directed Research", 1.0, 6500, false));
                courses.add(new Course("EEE498B", "Senior Design II", 1.5, 9750, false));
                courses.add(new Course("BIO103", "Biology I", 3.0, 19500, false));
                courses.add(new Course("BIO103L", "Biology I Lab", 3.0, 19500, false));
                courses.add(new Course("CHE101", "General Chemistry", 3.0, 19500, false));
                courses.add(new Course("CHE101L", "General Chemistry Lab", 1.0, 6500, false));
                courses.add(new Course("ENG102", "Introduction to Composition", 3.0, 19500, false));
                courses.add(new Course("ENG103", "Intermediate Composition", 3.0, 19500, false));
                courses.add(new Course("ENG111", "Public Speaking", 3.0, 19500, false));
                courses.add(new Course("ENG115", "Introduction to Literature", 3.0, 19500, false));
                courses.add(new Course("MAT116", "Pre-Calculus", 3.0, 19500, false));
                courses.add(new Course("MAT120", "Calculus and Analytical Geometry I", 3.0, 19500, false));
                courses.add(new Course("MAT125", "Introduction to Linear Algebra", 3.0, 19500, false));
                courses.add(new Course("MAT130", "Calculus and Analytical Geometry II", 3.0, 19500, false));
                courses.add(new Course("MAT250", "Calculus and Analytical Geometry IV", 3.0, 19500, false));
                courses.add(new Course("MAT350", "Engineering Mathematics", 3.0, 19500, false));
                courses.add(new Course("MAT361", "Probability and Statistics", 3.0, 19500, false));
                courses.add(new Course("MAT481", "Numerical Methods", 3.0, 19500, false));
                courses.add(new Course("PHI104", "Introduction to Ethics", 3.0, 19500, false));
                courses.add(new Course("PHY107", "Physics I", 3.0, 19500, false));
                courses.add(new Course("PHY107L", "Physics I Lab", 1.0, 6500, false));
                courses.add(new Course("PHY108", "Physics II", 3.0, 19500, false));
                courses.add(new Course("PHY108L", "Physics II Lab", 1.0, 6500, false));
                courses.add(new Course("POL101", "Introduction to Political Science", 3.0, 19500, false));
                courses.add(new Course("POL104", "Introduction to Governance", 3.0, 19500, false));
                courses.add(new Course("ENG102", "Introduction to Composition", 3.0, 19500, false));
                courses.add(new Course("ENG103", "Intermediate Composition", 3.0, 19500, false));
                courses.add(new Course("ENG111", "Public Speaking", 3.0, 19500, false));
                courses.add(new Course("ENG115", "Introduction to Literature", 3.0, 19500, false));
                
                break;
            }
            
            case "Architecture":
            {
                courses.add(new Course("ARC111", "Foundation Design Studio I- Artistic Development", 4.5, 29250, false));
                courses.add(new Course("ARC112", "Foundation Design Studio II-Form and Composition", 4.5, 29250, false));
                courses.add(new Course("ARC121", "Graphics I-Basic Drawing", 3.0, 19500, false));
                courses.add(new Course("ARC133", "Parameters in Design I-Aesthetics and Design", 1.5, 9750, false));
                courses.add(new Course("ARC200", "Art, Craft and Design-Observation and Documentation", 1.0, 6500, false));
                courses.add(new Course("ACT201", "Introduction to Financial Accounting", 3.0, 19500, false));
                courses.add(new Course("ARC214", "Architecture Design Studio I-Function and Analysis", 4.5, 29250, false));
                courses.add(new Course("ARC242", "Architectural Heritage II-World II", 3.0, 19500, false));
                courses.add(new Course("ARC262", "Environment And Building System II-Building Physics", 3.0, 19500, false));
                courses.add(new Course("ARC273", "Construction III-Workshop", 1.0, 6500, false));
                courses.add(new Course("ARC316", "Professional Studies Studio I-Form And Function", 4.5, 29250, false));
                courses.add(new Course("ARC334", "Parameters in Design II-Theory And Methods", 1.5, 9750, false));
                courses.add(new Course("ARC410", "Landscape Design Laboratory", 3.0, 19500, false));
                courses.add(new Course("ARC445", "Contemporary Design Precedents and Analysis", 2.0, 13000, false));
                courses.add(new Course("ARC474", "Construction IV-Documents and Management", 3.0, 19500, false));
                courses.add(new Course("ARC492", "Independent Study", 1.5, 9750, false));
                courses.add(new Course("ARC500", "Professional Internship (Supervised Training)", 1.0, 6500, false));
                courses.add(new Course("ARC596", "Thesis Documentation - Research and Development", 4.5, 29250, false));
                courses.add(new Course("ARC598", "Seminar on Art, Architecture, Society and Culture", 1.5, 9750, false));
                courses.add(new Course("ENG102", "Introduction to Composition", 3.0, 19500, false));
                courses.add(new Course("ENG103", "Intermediate Composition", 3.0, 19500, false));
                courses.add(new Course("ENG111", "Public Speaking", 3.0, 19500, false));
                courses.add(new Course("ENG115", "Introduction to Literature", 3.0, 19500, false));
                courses.add(new Course("MAT112", "Introduction to Elementary Mathematics", 3.0, 19500, false));
                
               
                break;
            }
            
            case "BBA":
            {
                courses.add(new Course("ACT201", "Introduction to Financial Accounting", 3.0, 19500, false));
                courses.add(new Course("ACT202", "Introduction to Managerial Accounting", 3.0, 19500, false));
                courses.add(new Course("ACT310", "Intermediate Accounting – I", 3.0, 19500, false));
                courses.add(new Course("ACT320", "Intermediate Accounting II", 3.0, 19500, false));
                courses.add(new Course("ACT360", "Advanced Managerial Accounting", 3.0, 19500, false));
                courses.add(new Course("ACT370", "Taxation", 3.0, 19500, false));
                courses.add(new Course("ACT380", "Audit and Assurance", 3.0, 19500, false));
                courses.add(new Course("ACT410", "Financial Statement Analysis", 3.0, 19500, false));
                courses.add(new Course("ACT430", "Accounting Information Systems", 3.0, 19500, false));
                courses.add(new Course("ACT460", "Advanced Financial Accounting", 3.0, 19500, false));
                courses.add(new Course("BUS112", "Introduction to Business Mathematics", 3.0, 19500, false));
                courses.add(new Course("BUS172", "Introduction to Statistics", 3.0, 19500, false));
                courses.add(new Course("BUS251", "Business Communication", 3.0, 19500, false));
                courses.add(new Course("BUS498", "Internship", 4.0, 26000, false));
                courses.add(new Course("MGT212", "Organizational Management", 3.0, 19500, false));
                courses.add(new Course("MGT314", "Production and Operations Management", 3.0, 19500, false));
                courses.add(new Course("MGT330", "Supply Chain Management", 3.0, 19500, false));
                courses.add(new Course("MGT489", "Research Project", 3.0, 19500, false));
                courses.add(new Course("MIS107", "Information System & Computing", 3.0, 19500, false));
                courses.add(new Course("MIS207", "E-Business", 3.0, 19500, false));
                courses.add(new Course("MKT202", "Introduction to Marketing", 3.0, 19500, false));
                courses.add(new Course("MKT330", "Digital Marketing", 3.0, 19500, false));
                courses.add(new Course("MKT344", "Consumer Behavior", 3.0, 19500, false));
                courses.add(new Course("MKT412", "Services Marketing", 3.0, 19500, false));
                courses.add(new Course("MKT445", "International Marketing", 3.0, 19500, false));
                courses.add(new Course("SCM310", "Introduction to Supply Chain Management", 3.0, 19500, false));
                courses.add(new Course("SCM420", "Sourcing and Procurement", 3.0, 19500, false));
                courses.add(new Course("SCM430", "Logistics and Distribution Management", 3.0, 19500, false));
                courses.add(new Course("SCM480", "Strategic Supply Chain Management", 3.0, 19500, false));
                courses.add(new Course("SCM610", "Strategic Supply Chain Management", 3.0, 19500, false));
                courses.add(new Course("SCM611", "Global Logistics and Transportation", 3.0, 19500, false));
                courses.add(new Course("SCM612", "Sourcing and Procurement Management", 3.0, 19500, false));
                courses.add(new Course("ENG102", "Introduction to Composition", 3.0, 19500, false));
                courses.add(new Course("ENG103", "Intermediate Composition", 3.0, 19500, false));
                courses.add(new Course("ENG111", "Public Speaking", 3.0, 19500, false));
                courses.add(new Course("ENG115", "Introduction to Literature", 3.0, 19500, false));
                
                break;

            }
        }

        coreTable.setItems(courses);
        for(int i =0; i<courses.size();i++){
            
            Course c = courses.get(i);
         //*

        c.selectProperty().addListener((obs, oldVal, newVal) -> {

        if (isUpdating) return;

        isUpdating = true;

        if (newVal) {
            if (currentCredits + c.getCredits() > maxCredits) {

                c.setSelected(false); // rollback
                warningLabel.setText("Credit limit exceeded!");

            } else {
                currentCredits += c.getCredits();
                warningLabel2.setText("Selected Credits: " + currentCredits + "/" + maxCredits);
            }

        } else {
            currentCredits -=  c.getCredits();
            warningLabel2.setText("Selected Credits: " + currentCredits + "/" + maxCredits);
        }

        isUpdating = false; //*
        
        updateTotals();  //*
    });
}
        

    } 
    
    private void updateTotals() {

        double totalCredits = 0;
        double totalFee = 0;

        for (Course c : coreTable.getItems())   //*
        {
            if (c.isSelected()){
                
            totalCredits += c.getCredits();
            totalFee += c.getFee();
            
            }
            
        }

        totalCreditsLabel.setText("Total Credits: " + totalCredits);
        totalFeeLabel.setText("Total Fee: " + totalFee);
    }
           
    @FXML
    private void confirmSelection(ActionEvent event) {

    ObservableList<Course> selectedCourses = FXCollections.observableArrayList();  //*

    for (int i = 0; i < coreTable.getItems().size(); i++) 
    {
    
         Course c = coreTable.getItems().get(i);
         
         if (c.isSelected()) 
         {
             
            selectedCourses.add(c);
    
         }
}  

    if (selectedCourses.isEmpty()) {
        warningLabel.setText("Select at least one course!");
        return;
    }

    double totalCredits = 0;

    for(int i=0; i<selectedCourses.size();i++){
        
        Course c = selectedCourses.get(i);
    
    
        totalCredits += c.getCredits();
    }

    if (selectedCourses.size() > maxCourses) {
        warningLabel.setText("Max courses: " + maxCourses);
        return;
    }

    if (totalCredits > maxCredits) {
        warningLabel.setText("Credit limit exceeded!");
        return;
    }

    try {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/unienroll/ui/fxml/Receipt.fxml")
        );

        Parent root = loader.load();

        Receipt_controller controller = loader.getController();
        controller.setData(selectedCourses);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        
        stage.setWidth(929);   
        stage.setHeight(700); 
        
        stage.setTitle("Receipt");
        
        stage.setResizable(false);
           
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    //going back
    @FXML
    private void back(ActionEvent event) throws IOException{
        
           Parent root3 = FXMLLoader.load(getClass().getResource("/unienroll/ui/fxml/Major_selection.fxml"));
           Stage stage3 = (Stage)((Node)event.getSource()).getScene().getWindow();
           
           Scene scene3= new Scene(root3);
           stage3.setScene(scene3);
           
           stage3.setWidth(929);   
           stage3.setHeight(700); 
           
           stage3.setTitle("Major Selection");
           
           stage3.getIcons().clear();
           stage3.getIcons().add(new Image(getClass().getResourceAsStream("major.png")));
           
           stage3.setResizable(false);
           
           stage3.show();
    }
}