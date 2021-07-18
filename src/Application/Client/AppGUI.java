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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
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
    public Button checkReminder;
    @FXML
    public Button displayGraphics;
    @FXML
    public Button logOutButton;
    
    @FXML
    public Button createTableButton;
    @FXML
    public Button searchTableButton;
    
    @FXML
    public TextArea tableTextArea;
    
    @FXML
    public Label loggedUsername;
    @FXML
    public Label loadedTableName;
    
    public Socket loginGUISocket;
   
    public Connection dbconn;
    public String srno;
    public String currenttable;
    
    
    //populated by controller from FXML file is the meaning of @FXML
    public void displayTabular(){

System.out.println("Tabular Display");
String sqlselect="SELECT * FROM databaseusers."+"`"+currenttable+"` ;";
String data="Upto 100 items being displayed\nsrno-----productname-----brand-----productidvalid-----productid-----batchid-----remainingquantity-----criticalquantity-----priceperpiece\n";
try{
if(currenttable!=null){

    PreparedStatement psForSelectAll=(com.mysql.jdbc.PreparedStatement)dbconn.prepareStatement(sqlselect);
    
    ResultSet rsForSelectAll = psForSelectAll.executeQuery();
    
    while(rsForSelectAll.next()){
     
        data+=rsForSelectAll.getInt(1)+"-----";
        
        for(int i=2;i<=6;i++)
            data+=rsForSelectAll.getString(i)+"-----";
                 
        for(int i=7;i<=9;i++){
        data+=rsForSelectAll.getInt(i);
        if(i<9) data+="-----";
        }
        data+="\n";
    }
    
    tableTextArea.setText(data);
}

}
    catch(SQLException ex){
         System.out.println("SQLEXCEPTION IN DISPLAYTABULAR OF SEARCHTABLE CLASS");
    }
  }
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
        insertiongui_ref.appgui_ref=this;
        
        }
        catch(SQLException e){
        System.out.println("ADDLISTENER sql");
        }
    }
   
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
        updategui_ref.appgui_ref=this;
        }
        catch(SQLException e){
        System.out.println("UPDATELISTENER sql");
        }
    }
    
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
        DeleteGUI deletegui_ref = fxmlLoader.getController();
        deletegui_ref.currenttable=currenttable;
        deletegui_ref.dbconn=dbconn;
        deletegui_ref.appgui_ref=this;
        }
        catch(SQLException e){
        System.out.println("DELETELISTENER sql");
        }
    }
    
    public void checkReminderListener(ActionEvent actionEvent) {
        
        System.out.println("checkReminderBtn Listener invoked");
        
        Parent root=null;
        Stage stage2 = new Stage();
        
        stage2.initOwner(stage);
        stage2.initModality(Modality.APPLICATION_MODAL);
        
       
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CheckReminderGUI.fxml"));
        try{
            root = fxmlLoader.load();
        }
        catch(IOException e){
            e.printStackTrace();
        }
       
        
        stage2.setTitle("Check Reminder");
        stage2.setScene(new Scene(root,500,429));
        stage2.show();
        CheckReminderGUI checkremindergui_ref = fxmlLoader.getController();
        checkremindergui_ref.currenttable=currenttable;
        checkremindergui_ref.dbconn=dbconn;
        
    }
    public void displayGraphical(ActionEvent actionevent){
    
        System.out.println("in displayGraphical AppGUI");
        
        Parent root=null;
        Stage stage2 = new Stage();
        
        stage2.initOwner(stage);
        stage2.initModality(Modality.APPLICATION_MODAL);
        stage2.setTitle("Display Graphical");
        /*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DisplayGraphicsGUI.fxml"));
        try{
            root = fxmlLoader.load();
        }
        catch(IOException e){
            e.printStackTrace();
        }*/
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Comparing prices, quantities and critical quantities across different brands");
        xAxis.setLabel("Productname");       
        yAxis.setLabel("Value");
        
        
        Scene scene  = new Scene(bc,600,429);
        //datapoints
        try{
        if(currenttable!=null){
        
            String sqlselect = "SELECT brand, batchid, remainingquantity, criticalquantity, priceperpiece FROM databaseusers."+
                   "`"+currenttable+"`"+" ORDER BY brand ASC LIMIT 0, 100;"; 
            
            PreparedStatement psForSqlSelect= (PreparedStatement)dbconn.prepareStatement(sqlselect);
            ResultSet rsForSqlSelect = psForSqlSelect.executeQuery();
            
            XYChart.Series obj1 = new XYChart.Series();
            obj1.setName("remainingquantity");
            XYChart.Series obj2 = new XYChart.Series();
            obj2.setName("criticalquantity");
            XYChart.Series obj3 = new XYChart.Series();
            obj3.setName("priceperpiece");
            
            while(rsForSqlSelect.next()){
                String strfullname=rsForSqlSelect.getString(1)+"-"+rsForSqlSelect.getString(2);
                obj1.getData().add(new XYChart.Data(strfullname,rsForSqlSelect.getInt(3)));
                
                obj2.getData().add(new XYChart.Data(strfullname,rsForSqlSelect.getInt(4)));
                obj3.getData().add(new XYChart.Data(strfullname,rsForSqlSelect.getInt(5)));
            
            }
          bc.getData().addAll(obj1, obj2, obj3);  
        }
            
        stage2.setScene(scene);
        stage2.show();
        
        stage2.show();
        }
        catch(SQLException ex){
        System.out.println("SQLEXCEPTION DISPLAYGRAPHICAL");
        }
        
    } 
    
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
    public void createTableListener(ActionEvent actionEvent){
    
        System.out.println("createTableBtn Listener invoked");
        
        Parent root=null;
        Stage stage2 = new Stage();
        
        stage2.initOwner(stage);
        stage2.initModality(Modality.APPLICATION_MODAL);
        
       
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateTableGUI.fxml"));
        try{
            root = fxmlLoader.load();
        }
        catch(IOException e){
            e.printStackTrace();
        }
       
        
        stage2.setTitle("Specify Name of Table");
        stage2.setScene(new Scene(root,300,200));
        stage2.show();
        
        CreateTableGUI createtablegui_ref=fxmlLoader.getController();
        createtablegui_ref.stage=stage2;
        createtablegui_ref.dbconn=this.dbconn;
        createtablegui_ref.srno=this.srno;
        createtablegui_ref.appgui_ref=this;
        System.out.println("AppGUI-side srno "+createtablegui_ref.srno);
        
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
