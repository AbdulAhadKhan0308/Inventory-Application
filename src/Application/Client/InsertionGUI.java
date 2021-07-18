package Application.Client;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.ResultSet;
import javafx.fxml.FXML;

public class InsertionGUI {
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField productidTextField;
    @FXML
    public TextField batchidTextField;
    @FXML
    public TextField isproductidValidTextField;
    @FXML
    public TextField brandTextField;
    @FXML
    public TextField remainquantityTextField;
    @FXML
    public TextField criticalquantityTextField;
    @FXML
    public TextField priceTextField;
    @FXML
    public Button addItemButton;
    public String currenttable;
    public Connection dbconn;
    public AppGUI appgui_ref;
    
    //public Stage stage;
    @FXML
    public void addItemListener(ActionEvent actionEvent) {
        
         String stringquerylastsrno="SELECT srno FROM databaseusers." +currenttable+" ORDER BY srno DESC LIMIT 1;";
         String stringqueryadd="INSERT INTO databaseusers."+currenttable+"(srno, productname, brand, productidvalid, productid, batchid, remainingquantity, criticalquantity, priceperpiece) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
         String stringqueryexists = "SELECT remainingquantity FROM databaseusers."+currenttable+" WHERE brand=? AND batchid=?;";
         
        System.out.println("Trying to add item to database table "+currenttable);
        System.out.println(stringquerylastsrno);
        
        try{
            int srno=1;
            PreparedStatement psForSerialNumber = (PreparedStatement)dbconn.prepareStatement(stringquerylastsrno);
            
            ResultSet rs = psForSerialNumber.executeQuery();
            
            if(rs.next()==true) srno = rs.getInt("srno")+1; 
            
            System.out.println("Serial number for insertion is "+srno);
            System.out.println("productidvalid "+isproductidValidTextField.getText());
            
        if(isproductidValidTextField.getText().equals("N")){
        
            PreparedStatement ps = (PreparedStatement)dbconn.prepareStatement(stringqueryadd);
            
            ps.setInt(1,srno);
            ps.setString(2, nameTextField.getText());
            ps.setString(3,brandTextField.getText());
            
            ps.setString(4,"N");
            ps.setString(5, "NA");
            ps.setString(6, productidTextField.getText());
            
            ps.setInt(7,Integer.parseInt(remainquantityTextField.getText()));
            ps.setInt(8,Integer.parseInt(criticalquantityTextField.getText()));
            ps.setInt(9, Integer.parseInt(priceTextField.getText()));
            
            System.out.println("sets done");
            System.out.println(ps);
            ps.executeUpdate();
            
            System.out.println("Successfully added");
        }
        else {
        
            PreparedStatement ps = (PreparedStatement)dbconn.prepareStatement(stringqueryexists);
            
            ps.setString(1,brandTextField.getText());
            ps.setString(2,batchidTextField.getText());
            System.out.println("ps for productidvalid=Y "+ps);
            ResultSet rs2 = ps.executeQuery();
            
            if(rs2.next()==true) {
            
            System.out.println("This item with productidvalid=Y exists");
            int updatedremainingquantity = rs2.getInt("remainingquantity")+1;
            String table_for_batchid = "`"+currenttable+"-"+batchidTextField.getText()+"`";
            String stringquerylastsrno2 = "SELECT srno FROM databaseusers." +table_for_batchid+" ORDER BY srno DESC LIMIT 1;";
            String stringqueryadd2 = "INSERT INTO databaseusers."+table_for_batchid+ "(`srno`, `productid`) VALUES (?, ?);";
            String stringqueryaddexisting = "UPDATE databaseusers."+"`"+currenttable+"`"+" SET remainingquantity=? WHERE brand=? AND batchid=?;";
            
            int srno2=1;
            PreparedStatement psForSerialNumber2 = (PreparedStatement)dbconn.prepareStatement(stringquerylastsrno2);
            System.out.println("psForSerialNumber2 "+psForSerialNumber2);
            ResultSet rsForSerialNumber2 = psForSerialNumber2.executeQuery();
            
            if(rsForSerialNumber2.next()==true) srno2 = rsForSerialNumber2.getInt("srno")+1; 
            System.out.println("Serial number for insertion when productidvalid=Y "+srno2);
            
            PreparedStatement ps_for_queryadd = (PreparedStatement)dbconn.prepareStatement(stringqueryadd2);
            ps_for_queryadd.setInt(1,srno2);
            ps_for_queryadd.setString(2,productidTextField.getText());
            
            System.out.println("ps_query_add "+ps_for_queryadd);
            ps_for_queryadd.executeUpdate();
            
            //Have to also increment the remaining quantity 
            PreparedStatement psAddExisting = (PreparedStatement)dbconn.prepareStatement(stringqueryaddexisting);
            psAddExisting.setInt(1,updatedremainingquantity);
            psAddExisting.setString(2,brandTextField.getText());
            psAddExisting.setString(3,batchidTextField.getText());
            System.out.println("psAddExisting "+psAddExisting);
            
            psAddExisting.executeUpdate();
            
            System.out.println("updated table for productidvalid=Y");
            
            }
            else  {
            
                System.out.println("This item with productidvalid=Y does not exists");
                PreparedStatement ps_queryadd_nonexisting = (PreparedStatement)dbconn.prepareStatement(stringqueryadd);
            
            ps_queryadd_nonexisting.setInt(1,srno);
            ps_queryadd_nonexisting.setString(2, nameTextField.getText());
            ps_queryadd_nonexisting.setString(3,brandTextField.getText());
            
            ps_queryadd_nonexisting.setString(4,"Y");
            ps_queryadd_nonexisting.setString(5, "NA");
            ps_queryadd_nonexisting.setString(6, batchidTextField.getText());
            
            ps_queryadd_nonexisting.setInt(7,1);//eariler this row didn't exist so now there is 1 product with this batchid
            ps_queryadd_nonexisting.setInt(8,Integer.parseInt(criticalquantityTextField.getText()));
            ps_queryadd_nonexisting.setInt(9, Integer.parseInt(priceTextField.getText()));
            
            System.out.println("ps_queryadd_nonexisting "+ps_queryadd_nonexisting);
            ps_queryadd_nonexisting.executeUpdate();
            
            //TABLE
            String createdTableName = "`"+currenttable+"-"+batchidTextField.getText()+"`";
            String string_createtable="CREATE TABLE `databaseusers`."+ createdTableName +"(`srno` INT NOT NULL, `productid` VARCHAR(45) NOT NULL, PRIMARY KEY (`srno`));";
            PreparedStatement ps_createtable = (PreparedStatement)dbconn.prepareStatement(string_createtable);
            
            System.out.println("ps_createdtable "+ps_createtable);
            ps_createtable.executeUpdate();
            
            //Adding item to newly created table
            String string_addtonewtable = "INSERT INTO databaseusers."+createdTableName+ "(`srno`, `productid`) VALUES (?, ?);";
            
            PreparedStatement ps_addtonewtable = (PreparedStatement)dbconn.prepareStatement(string_addtonewtable);
            ps_addtonewtable.setInt(1,1);
            ps_addtonewtable.setString(2,productidTextField.getText());
            System.out.println("ps_addtonewtable "+ps_addtonewtable);
            ps_addtonewtable.executeUpdate();
            
            System.out.println("Item whose batchid didn't exist added, its productid added to newly created table");
            }
        }
       
        appgui_ref.displayTabular();
        
        }
        catch(SQLException ex){
            System.out.println("Error Occured in adding item");
        }
    }

   
}
