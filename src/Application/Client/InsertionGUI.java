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
    
    //public Stage stage;
    @FXML
    public void addItemListener(ActionEvent actionEvent) {
        
         String stringquerylastsrno="SELECT srno FROM databaseusers." +currenttable+" ORDER BY srno DESC LIMIT 1;";
         String stringqueryadd="INSERT INTO databaseusers."+currenttable+"(srno, productname, brand, productidvalid, productid, batchid, remainingquantity, criticalquantity, priceperpiece) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        
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
            ps.setString(5, productidTextField.getText());
            ps.setString(6, batchidTextField.getText());
            
            ps.setInt(7,Integer.parseInt(remainquantityTextField.getText()));
            ps.setInt(8,Integer.parseInt(criticalquantityTextField.getText()));
            ps.setInt(9, Integer.parseInt(priceTextField.getText()));
            
            System.out.println("sets done");
            System.out.println(ps);
            ps.executeUpdate();
            
            System.out.println("Successfully updated");
        }
        else {
        }
        /*Socket socket = new Socket("localhost", 5436);
        System.out.println("Client created.");
        new InsertionGUI().sendMessage(socket,"Insertion",nameTextField.getText(),brandTextField.getText(),idTextField.getText(),quantityTextField.getText(),priceTextField.getText());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    String result=(String)objectInputStream.readObject();
                    if(result.equals("Success")){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Parent root = null;
                                Stage stage = (Stage) addItemButton.getScene().getWindow();
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SuccessGUI.fxml"));
                                try {
                                    root = (Parent) fxmlLoader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                stage.setTitle("Success GUI");
                                stage.setScene(new Scene(root, 400, 200));
                                stage.show();
                            }
                        });
                    }
                    else {
                        System.out.println("Message Recieved");
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Parent root = null;
                                Stage stage = (Stage) addItemButton.getScene().getWindow();
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FailureMessage.fxml"));
                                try {
                                    root = (Parent) fxmlLoader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                stage.setTitle("Failure GUI");
                                stage.setScene(new Scene(root, 250, 380));
                                stage.show();
                            }
                        });
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        */
        }
        catch(SQLException ex){
            System.out.println("Error Occured in adding item");
        }
    }

   /* private void sendMessage(Socket socket,String function,String name,String brand,String id,String quantity,String price) throws IOException{
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(function);
        objectOutputStream.flush();
        objectOutputStream.writeObject(name);
        objectOutputStream.flush();
        objectOutputStream.writeObject(brand);
        objectOutputStream.flush();
        objectOutputStream.writeObject(id);
        objectOutputStream.flush();
        objectOutputStream.writeObject(quantity);
        objectOutputStream.flush();
        objectOutputStream.writeObject(price);
        objectOutputStream.flush();

    }*/
}
