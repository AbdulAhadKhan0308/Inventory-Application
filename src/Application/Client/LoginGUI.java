package Application.Client;

import com.mysql.jdbc.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.io.BufferedReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Modality;

public class LoginGUI {

    public Label headLabel;
    public Label instructionLabel;
    public Label nameLabel;
    public Label passwordLabel;
    public TextField nameTextField;
    public PasswordField passwordField;
    public Button loginButton;
    public String querystr="SELECT srno FROM databaseusers.userinfo where username=? AND password=?;";
    private Socket loginGUISocket;
    public Stage stage;
    
    public void initialize(){
    System.out.println("initialize");
    try{
        loginGUISocket = new Socket("localhost",5000);
    }
    catch(UnknownHostException e){
    System.out.println("Unknown Host");
    }   
    catch (IOException ex) {
        System.out.println("IO Exception in LoginGUI constructor");
    }
    
    }
   
    
    private String sendLoginToServer(){
    try{
        BufferedReader input = new BufferedReader(new InputStreamReader(loginGUISocket.getInputStream()));
        PrintWriter output = new PrintWriter(loginGUISocket.getOutputStream(),true);
        
        System.out.println(nameTextField.getText());
        
        output.println(nameTextField.getText()+"$"+passwordField.getText());
        
        return input.readLine();
    }
    catch(IOException e){
    System.out.println("IO Exception in sendLoginToServer");
    }
        return "";
    }
    public void loginListener(ActionEvent actionEvent) throws IOException {
        //File file=new File("C:\\Users\\ABDUL AHAD KHAN\\Documents\\Password.txt");
        /*try {
            Scanner sc=new Scanner(file);
            while(sc.hasNextLine()){
                s=sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        try{
        String message = sendLoginToServer();
        System.out.println("Login  to server "+message);
        
        Parent root=null;
        
        if(!message.equals("0")) {
        
            String str[]=new String[3];
            int i,prv=0,j=0;
            
            for(i=0;i<message.length();i++){
            if(message.charAt(i)=='$') {
            str[j++]=message.substring(prv,i);
            prv=i+1;
            }
            }
            str[j++]=message.substring(prv,i);
            
           System.out.println("dbconn params "+str[0]+" "+str[1]+" "+str[2]);
           
            Connection dbconn = (Connection)DriverManager.getConnection(str[0],str[1],str[2]);
            
        Stage stage =(Stage) loginButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AppGUI.fxml"));
       
                
                root = loader.load();
            
           
            stage.setTitle("Inventory Application");
            stage.setScene(new Scene(root,849,586));
            stage.show();
            
            AppGUI appgui_ref= loader.getController();
            appgui_ref.loginGUISocket=this.loginGUISocket;
            appgui_ref.dbconn=dbconn;
            appgui_ref.stage=stage;
            appgui_ref.loggedUsername.setText(nameTextField.getText());
            appgui_ref.srno="";
            
            PreparedStatement ps = dbconn.prepareStatement(querystr);
            ps.setString(1,nameTextField.getText());
            ps.setInt(2,Integer.parseInt(passwordField.getText()));
            
            ResultSet rs = ps.executeQuery();
            rs.next();
            appgui_ref.srno=Integer.toString(rs.getInt("srno"));
            
            System.out.println("Login-side srno "+appgui_ref.srno);
        }
                    
        else {
           Stage stage =(Stage) loginButton.getScene().getWindow();
           FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("LoginFailureGUI.fxml"));
           try{
                root = fxmlloader.load();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            stage.setTitle("Login Failure");
            stage.setScene(new Scene(root,400,100));
            stage.show();
            
        }
        }
        catch(IOException e){
                e.printStackTrace();
        }
        catch(SQLException ex){
                System.out.println("dbconn NOT MADE");
                }
    }
}
