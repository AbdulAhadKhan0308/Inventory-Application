package Application.Client;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import javafx.application.Platform;
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
    
    public void updateItemListener(ActionEvent actionEvent) throws IOException {
        System.out.println("Update button pressed");
        String tableName="`"+currenttable+"`";
        String string_selectsrno="SELECT srno,productidvalid FROM databaseusers."+tableName+" WHERE brand=? AND batchid=?;"; 
        try{
        
            int srno=1;
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
            
            System.out.println("productidRowToBeUpdated "+productidRowToBeUpdated);
            
            if(productidRowToBeUpdated.equals("Y") && oldIsProductidValidTextField.getText().equals("Y")){
            System.out.println("verify productidRowToBeUpdated Y");
            
            }
            else if(productidRowToBeUpdated.equals("N") && oldIsProductidValidTextField.getText().equals("N")) {
            System.out.println("verify productidRowToBeUpdated N");
            
            
            }
            
            }
            else {
                
            System.out.println("NO SUITABLE ROW DETECTED TO UPDATE");
            }
            
        }
        
        catch(SQLException ex){
            System.out.println("Error Occured in updating item");
        }
        /*Socket socket = new Socket("localhost", 5436);
        System.out.println("Client created.");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject("Update");
        objectOutputStream.flush();
        objectOutputStream.writeObject(productIDTextField.getText());
        objectOutputStream.flush();
        objectOutputStream.writeObject(productNameTextField.getText());
        objectOutputStream.flush();
        objectOutputStream.writeObject(brandTextField.getText());
        objectOutputStream.flush();
        objectOutputStream.writeObject(quantityTextField.getText());
        objectOutputStream.flush();
        objectOutputStream.writeObject(priceTextField.getText());
        objectOutputStream.flush();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("recieving Message");
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    String result = (String) objectInputStream.readObject();
                    System.out.println("Message Recieved");
                    if(result.equals("Success")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Parent root = null;
                                Stage stage = (Stage) updateButton.getScene().getWindow();
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
                                Stage stage = (Stage) updateButton.getScene().getWindow();
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FailureMessage.fxml"));
                                try {
                                    root = (Parent) fxmlLoader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                stage.setTitle("Failure GUI");
                                stage.setScene(new Scene(root, 350, 180));
                                stage.show();
                            }
                        });
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
    }
}
