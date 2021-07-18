package Application.Client;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.sql.SQLException;
/**
 *
 * @author ABDUL AHAD KHAN
 */
public class CheckReminderGUI {
 @FXML
 public TextArea tableTextArea;
 @FXML
 public Button checkButton;
 
 String currenttable;
 Connection dbconn;
 
 public void checkReminderListener(ActionEvent actionEvent) {
     String sqlselect="SELECT srno,productname,brand,batchid,remainingquantity,criticalquantity FROM databaseusers."+"`"+
             currenttable+"` " +"WHERE remainingquantity<=criticalquantity;";
     String data="";
     System.out.println("checkButton in setReminderListener clicked");
     try{
     if(currenttable!=null){
     
         System.out.println("Table exists");
         PreparedStatement psForSqlSelect = (PreparedStatement)dbconn.prepareStatement(sqlselect);
         
         ResultSet rsForSelectSql = psForSqlSelect.executeQuery();
         
            data+="srno-----productname-----brand-----batchid-----remainingquantity-----criticalquantity\n";   
             
             while(rsForSelectSql.next()==true){
                 
                 data+=rsForSelectSql.getInt(1)+"-----";
                 for(int i=2;i<=4;i++)
                 data+=rsForSelectSql.getString(i)+"-----";
                 
                 for(int i=5;i<=6;i++){
                 data+=rsForSelectSql.getInt(i);
                 if(i==5) data+="-----";
                 }
                 
                 
                 data+="\n";
             }
             tableTextArea.setText(data);
     }
     }
     catch(SQLException ex){
     System.out.println("SQLEXCEPTION IN CHECKREMINDERLISTENER");
     }
 }
 
}
