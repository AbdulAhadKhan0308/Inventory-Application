package Application.Client;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import java.sql.ResultSet;

public class UpdateGUI {
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField productidTextField;
    @FXML
    public TextField batchidTextField;
    @FXML
    public TextField isproductidValidTextField;
    @FXML
    public TextField oldIsProductidValidTextField;
    @FXML
    public TextField oldProductidTextField;
    @FXML
    public TextField brandTextField;
    @FXML
    public TextField oldBrandTextField;
    @FXML
    public TextField oldBatchidTextField;
    @FXML
    public TextField remainquantityTextField;
    @FXML
    public TextField criticalquantityTextField;
    @FXML
    public TextField priceTextField;
    @FXML
    public Button updateItemButton;
    public String currenttable;
    public Connection dbconn;
    public AppGUI appgui_ref;
    
    public void updateItemListener(ActionEvent actionEvent) throws IOException {
        System.out.println("Update button pressed");
        String tableName="`"+currenttable+"`";
        String string_selectsrno="SELECT srno,productidvalid FROM databaseusers."+tableName+" WHERE brand=? AND batchid=?;";
        String string_update= "UPDATE databaseusers."+tableName+ " SET productname=?,brand=?,productidvalid=?,productid=?,batchid=?,remainingquantity=?,criticalquantity=?,priceperpiece=? WHERE srno=?;";
        try{
        
            System.out.println("oldBrand "+oldBrandTextField.getText());
            System.out.println("oldBatchid "+oldBatchidTextField.getText());
            
            PreparedStatement psForSerialNumber = (PreparedStatement)dbconn.prepareStatement(string_selectsrno);
            psForSerialNumber.setString(1, oldBrandTextField.getText());
            psForSerialNumber.setString(2,oldBatchidTextField.getText());
            System.out.println("psForSerialNumber "+psForSerialNumber);
            
            ResultSet rsForSerialNumber = psForSerialNumber.executeQuery();
            
            if(rsForSerialNumber.next()==true){
            System.out.println("Row found to be updated");
            
            String productidRowToBeUpdated = rsForSerialNumber.getString("productidvalid");
            int srno = rsForSerialNumber.getInt("srno");
            
            System.out.println("productidRowToBeUpdated "+productidRowToBeUpdated);
            
            if(productidRowToBeUpdated.equals("Y") && oldIsProductidValidTextField.getText().equals("Y")){
            System.out.println("verify productidRowToBeUpdated Y");
            
            String newTableName = "`"+currenttable+"-"+oldBatchidTextField.getText()+"`";
            String string_selectnewtable = "SELECT srno,productid FROM databaseusers."+newTableName+" WHERE productid=?;";
            String string_updatetable = "UPDATE databaseusers."+tableName+ " SET productname=?,brand=?,productidvalid=?,productid=?,remainingquantity=?,criticalquantity=?,priceperpiece=? WHERE srno=?;";
            String string_updatenewtable = "UPDATE databaseusers."+newTableName+ " SET productid=? WHERE productid=?;";
            
            PreparedStatement psForSelectNewTable = (PreparedStatement)dbconn.prepareStatement(string_selectnewtable);
            psForSelectNewTable.setString(1,oldProductidTextField.getText());
            System.out.println("psForSelectNewTable "+psForSelectNewTable);
            
            ResultSet rsForSelectNewTable = psForSelectNewTable.executeQuery();
            
            if(oldProductidTextField.getText().isBlank() || rsForSelectNewTable.next()==true){
            
                System.out.println("either productid is absent or productid found");
                PreparedStatement psForUpdateTable = (PreparedStatement)dbconn.prepareStatement(string_updatetable);
                
                psForUpdateTable.setString(1,nameTextField.getText());
                psForUpdateTable.setString(2,brandTextField.getText());
                psForUpdateTable.setString(3,"Y");
                psForUpdateTable.setString(4, "NA");
               
                psForUpdateTable.setInt(5,Integer.parseInt(remainquantityTextField.getText()));
                psForUpdateTable.setInt(6,Integer.parseInt(criticalquantityTextField.getText()));
                psForUpdateTable.setInt(7, Integer.parseInt(priceTextField.getText()));
                psForUpdateTable.setInt(8,srno);
                System.out.println("psUpdate "+ psForUpdateTable);
            
                psForUpdateTable.executeUpdate();
                System.out.println("table update complete");
                
               //if product id is found in 11inventory-143 table then it's value gets changed(update)
                PreparedStatement psForUpdateNewTable = (PreparedStatement)dbconn.prepareStatement(string_updatenewtable);
                psForUpdateNewTable.setString(1,productidTextField.getText());
                psForUpdateNewTable.setString(2,oldProductidTextField.getText());
                
                System.out.println("psForUpdateNewTable "+psForUpdateNewTable);
                
                psForUpdateNewTable.executeUpdate();
                
            System.out.println("Successfully updated");
            }
            
            }
            else if(productidRowToBeUpdated.equals("N") && oldIsProductidValidTextField.getText().equals("N")) {
            System.out.println("verify productidRowToBeUpdated N");
            
            PreparedStatement psUpdate = (PreparedStatement)dbconn.prepareStatement(string_update);
            psUpdate.setString(1,nameTextField.getText());
            psUpdate.setString(2,brandTextField.getText());
            psUpdate.setString(3,"N");
            psUpdate.setString(4, "NA");
            psUpdate.setString(5,batchidTextField.getText());
           
            psUpdate.setInt(6,Integer.parseInt(remainquantityTextField.getText()));
            psUpdate.setInt(7,Integer.parseInt(criticalquantityTextField.getText()));
            psUpdate.setInt(8, Integer.parseInt(priceTextField.getText()));
            psUpdate.setInt(9,srno);
            System.out.println("psUpdate "+ psUpdate);
            
            psUpdate.executeUpdate();
            
            System.out.println("Successfully updated");
            }
            
            }
            else {
                
            System.out.println("NO SUITABLE ROW DETECTED TO UPDATE");
            }
            appgui_ref.displayTabular();
        }
        
        catch(SQLException ex){
            System.out.println("Error Occured in updating item");
        }
       
    }
}
