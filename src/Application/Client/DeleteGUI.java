
package Application.Client;

import java.io.IOException;
import java.net.Socket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author ABDUL AHAD KHAN
 */
public class DeleteGUI {
    
    @FXML
    public Button deleteButton;
    @FXML
    public TextField productIDTextField;
    @FXML
    public TextField batchIDTextField;
    @FXML
    public TextField productValidTextField;
    
    private Socket LoginGUISocket;
    
    
    public void deleteListener(ActionEvent actionEvent) throws IOException {
    System.out.println("delete button pressed");
    }
}
