package Application.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class ContentDisplayGUI {

    public TextArea contentTextArea;
    public Button logoutButton;
    public Button backButton;

    public void logoutListener(ActionEvent actionEvent) {
        StartApplication.logout();
    }

    public void backListener(ActionEvent actionEvent) {
        Parent root=null;
        Stage stage =(Stage) logoutButton.getScene().getWindow();
        try{
            root = FXMLLoader.load(getClass().getResource("FunctionalityGUI.fxml"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        stage.setTitle("Food Pile Application");
        stage.setScene(new Scene(root,400,250));
        stage.show();
    }
}
