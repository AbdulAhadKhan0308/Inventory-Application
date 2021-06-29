package Application.Client;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author ABDUL AHAD KHAN
 */
public class SureLogOutGUI {
    
    Stage stage;
    AppGUI appgui_ref;
    @FXML
    Button takeBackButton;
    @FXML
    Button LogOutButton;
   
    public void takeBackListener(ActionEvent e) throws IOException{
    System.out.println("takeBack");
    System.out.println("Stage dimensions "+stage.heightProperty()+" "+stage.widthProperty());
    stage.close();
    }
    public void LogOutListener(ActionEvent e) throws IOException{
    System.out.println("LogOut");
    
    appgui_ref.sureLoggedOut();
    stage.close();
    
    }
}
