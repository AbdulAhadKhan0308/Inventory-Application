package Application.Client;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.SQLException;

/**
 *
 * @author ABDUL AHAD KHAN
 */
public class CreateTableGUI {
    @FXML
    public TextField tablenameTextField;
    @FXML
    public Button enterButton;
    public Connection dbconn;
    public AppGUI appgui_ref;
    public Stage stage;
    public String srno;
    
    public void enterButtonListener(ActionEvent actionEvent){
    System.out.println("Enter Button in CreateTableGUI pressed");
    
    String sqlselect = "SELECT srno FROM databaseusers.tablesofuser"+srno+ " ORDER BY srno DESC LIMIT 1";
    String sqlselectunique = "SELECT tablename FROM databaseusers.tablesofuser"+srno+" WHERE tablename=?";
    String sqlinsert = "INSERT INTO databaseusers.tablesofuser"+srno+ " (srno,tablename) VALUES (?,?)";
    String sqlcreatetable1 = "CREATE TABLE `databaseusers`."; 
    String sqlcreatetable2 = " (`srno` INT NOT NULL, `productname` VARCHAR(45) NOT NULL, "
            + " `brand` VARCHAR(45) NOT NULL, "+
  "`productidvalid` VARCHAR(45) NOT NULL,"+
  "`productid` VARCHAR(45) NOT NULL,"+
  "`batchid` VARCHAR(45) NOT NULL,"+
  "`remainingquantity` INT NOT NULL,"+
  "`criticalquantity` INT NOT NULL,"+
  "`priceperpiece` INT NOT NULL,"+
  "PRIMARY KEY (`srno`));";

    
    try{
    PreparedStatement psForSelectSrno = (PreparedStatement)dbconn.prepareStatement(sqlselect);
    
    System.out.println(psForSelectSrno);
    ResultSet rsForSelectSrno = psForSelectSrno.executeQuery();
    
    int srno=1;
    
    if(rsForSelectSrno.next()==true) srno = rsForSelectSrno.getInt("srno")+1;
    
    System.out.println("Srno is "+srno);
    PreparedStatement psForSelectUnique = (PreparedStatement)dbconn.prepareStatement(sqlselectunique);
    psForSelectUnique.setString(1,tablenameTextField.getText());
    
    System.out.println(psForSelectUnique);
    ResultSet rsForSelectUnique = psForSelectUnique.executeQuery();
    
    if(rsForSelectUnique.next()==false){
        
        PreparedStatement psForInsert = (PreparedStatement)dbconn.prepareStatement(sqlinsert);
        psForInsert.setInt(1,srno);
        psForInsert.setString(2,tablenameTextField.getText());
        System.out.println(psForInsert);
        
        psForInsert.executeUpdate();
        
        String tablenametoadd="`"+this.srno+Integer.toString(srno)+tablenameTextField.getText()+"`";
        PreparedStatement psForCreateTable = (PreparedStatement)dbconn.prepareStatement(sqlcreatetable1+tablenametoadd+sqlcreatetable2);
        System.out.println(psForCreateTable);
        
        psForCreateTable.executeUpdate();
        
        System.out.println("Successfully created table");
    }
    
    }
    
    catch (SQLException ex){
    System.out.println("SQLEXCEPTION IN CREATETABLE");
    }
}
}
