/*package Application.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.fxml.FXML;

public class FunctionalityGUI {

    public Button logoutButton;
    public Button deletionButton;
    public Button insertionButton;
    public Button updateButton;
    public Button displayButton;
    public Button searchButton;
    public Label functionLabel;
    @FXML
    private Label funtionLabel;

    @FXML
    public void logoutListener(ActionEvent actionEvent) {
        StartApplication.logout();
    }

    @FXML
    public void deletionListener(ActionEvent actionEvent) {
        Parent root=null;
        Stage stage =(Stage) deletionButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SearchChoice.fxml"));
        try{
            root = fxmlLoader.load();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        SearchChoice searchChoice=fxmlLoader.<SearchChoice>getController();
        searchChoice.functionLabel.setText("Deletion-Window");
        stage.setTitle("Select Delete");
        stage.setScene(new Scene(root,400,200));
        stage.show();
    }

    @FXML
    public void insertionListener(ActionEvent actionEvent) {
        Parent root=null;
        Stage stage =(Stage) insertionButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InsertionGUI.fxml"));
        try{
            root = fxmlLoader.load();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        stage.setTitle("Select Delete");
        stage.setScene(new Scene(root,500,300));
        stage.show();
    }

    @FXML
    public void updateListener(ActionEvent actionEvent) {
        Parent root=null;
        Stage stage =(Stage) deletionButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateGUI.fxml"));
        try{
            root = fxmlLoader.load();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        stage.setTitle("Update Item Details");
        stage.setScene(new Scene(root,450,250));
        stage.show();
    }

    @FXML
    public void displayListener(ActionEvent actionEvent) {
        Parent root=null;
        Stage stage =(Stage) searchButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SelectSorting.fxml"));
        try{
            root = fxmlLoader.load();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        stage.setTitle("Select Sorting parameter");
        stage.setScene(new Scene(root,400,200));
        stage.show();
    }

    @FXML
    public void searchListener(ActionEvent actionEvent) {
        Parent root=null;
        Stage stage =(Stage) searchButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SearchChoice.fxml"));
        try{
            root = fxmlLoader.load();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        SearchChoice searchChoice=fxmlLoader.<SearchChoice>getController();
        searchChoice.functionLabel.setText("Search-Window");
        stage.setTitle("Select Search");
        stage.setScene(new Scene(root,400,200));
        stage.show();
    }
}*/
