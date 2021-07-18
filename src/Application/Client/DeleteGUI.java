package Application.Client;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.SQLException;
/**
 *
 * @author ABDUL AHAD KHAN
 */
public class DeleteGUI {
    
    @FXML
    public Button deleteButton;
    @FXML
    public TextField productidTextField;
    @FXML
    public TextField batchidTextField;
    @FXML
    public TextField productidValidTextField;
    @FXML
    public TextField brandTextField;
    String currenttable;
    Connection dbconn;
    AppGUI appgui_ref;
    
    public void deleteListener(ActionEvent actionEvent) throws IOException {
    System.out.println("delete button pressed");
    
    String brand = brandTextField.getText();
    String batchid = batchidTextField.getText();
    String productidvalid = productidValidTextField.getText();
    String productid = productidTextField.getText();
    String selectquery="SELECT srno,remainingquantity FROM databaseusers."+ "`"+currenttable+"`"+ " WHERE brand=? AND batchid=?;";
    String deletequery = "DELETE FROM databaseusers."+ "`"+currenttable+"`"+ " WHERE srno=?;";
    
    
    try{
            PreparedStatement psSelect = (PreparedStatement)dbconn.prepareStatement(selectquery);
            PreparedStatement psDelete = (PreparedStatement)dbconn.prepareStatement(deletequery);
            psSelect.setString(1, brandTextField.getText());
            psSelect.setString(2,batchidTextField.getText());
            System.out.println("psSelect "+psSelect);
            
            ResultSet rs = psSelect.executeQuery();
            
            System.out.println("Select executed");
    
        if(rs.next()==true){
        
        int srno = rs.getInt(1);
        psDelete.setInt(1, srno);
        System.out.println("psDelete "+psDelete);
        
        if(productidvalid.equals("N")) {
           psDelete.executeUpdate();
    }
    else {
        
            if(productid.isBlank()){
                
                String tabletodrop = "`"+currenttable+"-"+batchid+"`";
                String droptablequery="DROP TABLE "+tabletodrop+";";
                psDelete.executeUpdate();
        
                PreparedStatement psDropTable = (PreparedStatement)dbconn.prepareStatement(droptablequery);
                System.out.println("psDropTable "+psDropTable);
        
                psDropTable.executeUpdate();
            }
            else {
                
                int updatedremainingquantity=rs.getInt(2)-1;
                final String updatequery="UPDATE databaseusers."+ "`"+currenttable+"`"+ " SET remainingquantity=? WHERE srno=?";
                final String selectproductidtable="SELECT srno FROM databaseusers."+ "`"+currenttable+"-"+batchid+"`"+" WHERE productid=?;";
                final String deleteproductidtable="DELETE FROM databaseusers."+"`"+currenttable+"-"+batchid+"`"+" WHERE srno =?;";
                
                PreparedStatement psUpdate = (PreparedStatement)dbconn.prepareStatement(updatequery);
                psUpdate.setInt(1, updatedremainingquantity);
                psUpdate.setInt(2,srno);
                System.out.println("psUpdate "+psUpdate);
                
                psUpdate.executeUpdate();
                
                PreparedStatement psSelectProductidTable = (PreparedStatement)dbconn.prepareStatement(selectproductidtable);
                psSelectProductidTable.setString(1, productid);
                System.out.println("psSelectProductidTable "+psSelectProductidTable);
                
                ResultSet rsproductidtable=psSelectProductidTable.executeQuery();
                
                if(rsproductidtable.next()==true){
                    System.out.println("About to cause productid deletion");
                    
                int srnoproductidtable=rsproductidtable.getInt(1);
                PreparedStatement psDeleteProductidTable = (PreparedStatement)dbconn.prepareStatement(deleteproductidtable);
                psDeleteProductidTable.setInt(1, srnoproductidtable);
                System.out.println("psProductid "+psDeleteProductidTable);
                
                psDeleteProductidTable.executeUpdate();
                
                }
            }
        }
    System.out.println("Successful deletion");
    
    }
    appgui_ref.displayTabular();
    }
    catch(SQLException ex){
     
        System.out.println("ERROR IN DELETING ROW");
        //!!!
        //a window pops failure
    }
    
    }
}
