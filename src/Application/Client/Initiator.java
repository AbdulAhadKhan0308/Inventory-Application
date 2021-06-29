package Application.Client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Initiator extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root=null;
            primaryStage = new Stage();
            
            FXMLLoader fxmlloader=new FXMLLoader(getClass().getResource("LoginGUI.fxml"));
            root=fxmlloader.load();
            
            primaryStage.setTitle("Login Window");
            primaryStage.setScene(new Scene(root, 300, 200));
            primaryStage.show();
            
            LoginGUI logingui_ref =fxmlloader.getController();
            logingui_ref.stage = primaryStage;
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

}
