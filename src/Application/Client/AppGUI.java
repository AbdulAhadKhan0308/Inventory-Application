package Application.Client;

import com.mysql.jdbc.Connection;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.Socket;
import javafx.stage.Modality;


public class AppGUI {
    @FXML
    public Stage stage;
    @FXML
    public Button addButton;
    @FXML
    public Button updateButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button displayButton;
    @FXML
    public Button setReminder;
    @FXML
    public Button displayGraphics;
    @FXML
    public Button logOutButton;
    
    @FXML
    public Button createTableButton;
    @FXML
    public Button searchTableButton;
    @FXML
    public Button searchItemButton;
    
    @FXML
    public Label loggedUsername;
    @FXML
    public Label loadedTableName;
    
    public Socket loginGUISocket;
   
    public Connection dbconn;
    public String srno;
    public String currenttable;
    
    /*AppGUI(){
        /*stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AppGUI.fxml"));
        
        loader.setRoot(this);
        loader.setController(this);
        
        try{
        Parent root = loader.load();
        stage.setScene(new Scene(root,849,586));
        stage.setTitle("Inventory App");
        stage.showAndWait();
        
        }
        catch(IOException e){
        System.out.println("Inventory App GUI not created");
        }
    }*/
    //populated by controller from FXML file is the meaning of @FXML
    @FXML
    public void addButtonListener(ActionEvent actionEvent) {
        try{
        System.out.println("addBtn Listener invoked");
        System.out.println(dbconn.isClosed()+"is closed? ");
        
        Parent root=null;
        Stage stage2 = new Stage();
        
        stage2.initOwner(stage);
        stage2.initModality(Modality.APPLICATION_MODAL);
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InsertionGUI.fxml"));
        try{
            root = fxmlLoader.load();
        }
        catch(IOException e){
            e.printStackTrace();
        }
       
        
        stage2.setTitle("Specify Item to Add");
        stage2.setScene(new Scene(root,500,429));
        stage2.show();
        InsertionGUI insertiongui_ref = fxmlLoader.getController();
        insertiongui_ref.currenttable=currenttable;
        insertiongui_ref.dbconn=dbconn;
        
        }
        catch(SQLException e){
        System.out.println("ADDLISTENER sql");
        }
    }
    @FXML
    public void updateListener(ActionEvent actionEvent) {
        try{
        System.out.println("updateBtn Listener invoked");
        System.out.println(dbconn.isClosed()+"is closed? ");
        
        Parent root=null;
        Stage stage2 = new Stage();
        
        stage2.initOwner(stage);
        stage2.initModality(Modality.APPLICATION_MODAL);
        
        //stage2 =(Stage) addButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateGUI.fxml"));
        try{
            root = fxmlLoader.load();
        }
        catch(IOException e){
            e.printStackTrace();
        }
       
        
        stage2.setTitle("Specify Item to Update");
        stage2.setScene(new Scene(root,500,650));
        stage2.show();
        UpdateGUI updategui_ref = fxmlLoader.getController();
        updategui_ref.currenttable=currenttable;
        updategui_ref.dbconn=dbconn;
        }
        catch(SQLException e){
        System.out.println("UPDATELISTENER sql");
        }
    }
     @FXML
    public void deleteListener(ActionEvent actionEvent) {
        try{
        System.out.println("deleteBtn Listener invoked");
        System.out.println(dbconn.isClosed()+"is closed? ");
        
        Parent root=null;
        Stage stage2 = new Stage();
        
        stage2.initOwner(stage);
        stage2.initModality(Modality.APPLICATION_MODAL);
        
       
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeleteGUI.fxml"));
        try{
            root = fxmlLoader.load();
        }
        catch(IOException e){
            e.printStackTrace();
        }
       
        
        stage2.setTitle("Specify Item to Delete");
        stage2.setScene(new Scene(root,374,262));
        stage2.show();
        }
        catch(SQLException e){
        System.out.println("DELETELISTENER sql");
        }
    }
    @FXML
    public void LogOutListener(ActionEvent actionEvent) {
        try{
        System.out.println("logOutBtn Listener invoked");
        System.out.println(dbconn.isClosed()+"is closed? ");
        
        Parent root=null;
        Stage stage2 = new Stage();
        
        stage2.initOwner(stage);
        stage2.initModality(Modality.APPLICATION_MODAL);
        
       
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SureLogOutGUI.fxml"));
        try{
            root = fxmlLoader.load();
        }
        catch(IOException e){
            e.printStackTrace();
        }
       
        
        stage2.setTitle("LOGOUT");
        stage2.setScene(new Scene(root,300,175));
        stage2.show();
        
        SureLogOutGUI surelogoutgui_ref=fxmlLoader.getController();
        surelogoutgui_ref.stage=stage2;
        surelogoutgui_ref.appgui_ref=this;
        }
        catch(SQLException e){
        System.out.println("LOGOUTLISTENER sql");
        }
        
    }
    public void searchTableListener(ActionEvent actionEvent){
    try{
        System.out.println("searchTableBtn Listener invoked");
        System.out.println(dbconn.isClosed()+"is closed? ");
        
        Parent root=null;
        Stage stage2 = new Stage();
        
        stage2.initOwner(stage);
        stage2.initModality(Modality.APPLICATION_MODAL);
        
       
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SearchTableGUI.fxml"));
        try{
            root = fxmlLoader.load();
        }
        catch(IOException e){
            e.printStackTrace();
        }
       
        
        stage2.setTitle("Specify Item to Search");
        stage2.setScene(new Scene(root,300,200));
        stage2.show();
        
        SearchTableGUI searchtablegui_ref=fxmlLoader.getController();
        searchtablegui_ref.stage=stage2;
        searchtablegui_ref.dbconn=this.dbconn;
        searchtablegui_ref.srno=this.srno;
        searchtablegui_ref.appgui_ref=this;
        System.out.println("AppGUI-side srno "+searchtablegui_ref.srno);
        }
        catch(SQLException e){
        System.out.println("SEARCHTABLE sql");
        }
        
    }
    public void sureLoggedOut(){
    
        System.out.println("sureLoggedOut invoked");
        
    
    try {
            loginGUISocket.close();
            dbconn.close();
            stage.close();
            Stage loginStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("LoginGUI.fxml"));
            loginStage.setTitle("Login Window");
            loginStage.setScene(new Scene(root, 300, 200));
            
            loginStage.show();
        }
        catch(IOException e){
           System.out.println("LOGIN STAGE NOT CREATED/LOGINGUISOCKET NOT CLOSED");
        }
        catch(SQLException e){
        System.out.println("ADD LISTENER FAIL");
        }
    }
}
