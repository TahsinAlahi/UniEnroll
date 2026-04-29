package unienroll.example;
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
import javafx.stage.Stage;

public class Student_info_controller implements javafx.fxml.Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Label label2, label3, label4, label5, label6;

    @FXML
    private TextField Name, ID, CGPA, Credits;
    
    @FXML
    private Button nextButton, backButton;
    
    private final unienroll.ui.SessionState sessionState;
    private final unienroll.application.MemberService memberService;
    private final unienroll.application.CourseService courseService;

    public Student_info_controller(unienroll.application.MemberService memberService, unienroll.application.CourseService courseService) {
        this.sessionState = unienroll.ui.SessionState.getInstance();
        this.memberService = memberService;
        this.courseService = courseService;
    }

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        unienroll.domain.Member member = sessionState.getLoggedInMember();
        if (member instanceof unienroll.domain.Student) {
            unienroll.domain.Student student = (unienroll.domain.Student) member;
            Name.setText(student.getName());
            
            ID.setText(student.getId());
            ID.setEditable(false);
            ID.setDisable(true); // Make it clearly non-editable
            
            double totalCredits = courseService.calculateTotalCredits(student);
            Credits.setText(String.valueOf(totalCredits));
            Credits.setEditable(false);
            Credits.setDisable(true);
            
            CGPA.setText(String.valueOf(student.getCgpa()));
        }
    }

    String name;
    String idStr;
    public static double cgpa; // kept for compatibility with other team controllers
    double credits;

    @FXML
    private void Next(ActionEvent event) throws IOException {
        
        if (Name.getText().isEmpty() || CGPA.getText().isEmpty()) {
            label6.setText("Please provide all the information first");
            return;
        }

        try {
            cgpa = Double.parseDouble(CGPA.getText());
            
            // Update domain model
            unienroll.domain.Student student = (unienroll.domain.Student) sessionState.getLoggedInMember();
            student.setCgpa(cgpa);
            student.setName(Name.getText());
            memberService.updateMember(student);

        } catch(Exception e){
            label6.setText("Enter a valid CGPA");
            return;
        }
        
        loadScene(event, "Department_selection.fxml", "Department Selection", "department_icon.png");
    }
    
    @FXML
    private void Back (ActionEvent event) throws IOException {
        loadScene(event, "Data_input.fxml", "Student Login", "Human_icon.jpg");
    }

    private void loadScene(ActionEvent event, String fxml, String title, String icon) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.setControllerFactory(unienroll.ui.MainApp.getControllerFactory());
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.getIcons().clear();
        stage.getIcons().add(new Image(getClass().getResourceAsStream(icon)));
        stage.show();
    }
}