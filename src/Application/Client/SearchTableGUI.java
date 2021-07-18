
package Application.Client;

import com.mysql.jdbc.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author ABDUL AHAD KHAN
 */
public class SearchTableGUI {
    @FXML
    public TextField tablenameTextField;
    @FXML
    public Button enterButton;
    public Connection dbconn;
    public AppGUI appgui_ref;
    public Stage stage;
    public String srno;
    
    
    public void enterButtonListener(ActionEvent actionEvent){
    String querystring="SELECT srno,tablename FROM databaseusers.tablesofuser"+srno+" WHERE tablename=?;";
        
    try{
        System.out.println("enter in searchTable pressed");
    
    PreparedStatement ps = dbconn.prepareStatement(querystring);
    ps.setString(1,tablenameTextField.getText());
        
    ResultSet rs = ps.executeQuery();
    
    if(rs.next()) {
        appgui_ref.currenttable=srno+Integer.toString(rs.getInt("srno"))+rs.getString("tablename");
        System.out.println("CurrentTable "+appgui_ref.currenttable);
        appgui_ref.loadedTableName.setText(appgui_ref.currenttable);
        //appgui_ref.tableTextArea.setEditable(false);
    //display
    appgui_ref.displayTabular();
    }
    else {
    System.out.println("No such Table");
    }
    
    
    }
    catch(SQLException e){
        System.out.println("SQLEXCEPTION enterinSearchTable");
    }
    finally{
    stage.close();
    }
    
}


}
