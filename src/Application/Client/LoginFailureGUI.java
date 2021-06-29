package Application.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFailureGUI {
    public Button retryButton;
    public Label FailureMessage;

    public void retryListener(ActionEvent actionEvent) {
        Parent root=null;
        Stage stage =(Stage) retryButton.getScene().getWindow();
        try{
            root = FXMLLoader.load(getClass().getResource("LoginGUI.fxml"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        stage.setTitle("Login GUI");
        stage.setScene(new Scene(root, 300, 200));
        stage.show();
    }
}