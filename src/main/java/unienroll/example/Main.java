package unienroll.example;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
    
    @Override
    public void start(Stage stage) {
        try {
           
        //System.out.println(getClass().getResource("fxml.fxml"));
           Parent root1 = FXMLLoader.load(getClass().getResource("Data_input.fxml"));
           Scene scene1 = new Scene(root1);
        
           stage.setWidth(800);   
           stage.setHeight(450); 
           
           stage.setTitle("Student Login");
           
           stage.getIcons().add(new Image(getClass().getResourceAsStream("Human_icon.jpg")));
           
           stage.setResizable(false);
           
                  
           stage.setScene(scene1);
           stage.show();
    }

  
     
        catch(Exception e){
            e.printStackTrace();
        }
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}

