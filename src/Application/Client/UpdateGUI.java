package Application.Client;

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
import javafx.fxml.FXML;

public class UpdateGUI {
    @FXML
    public Button updateButton;
    @FXML
    public TextField productIDTextField;
    /*
    public TextField productNameTextField;
    public TextField brandTextField;
    public TextField quantityTextField;
    public TextField priceTextField;*/
    @FXML
    public TextField batchIDTextField;
    @FXML
    public TextField productValidTextField;
    
    public void updateListener(ActionEvent actionEvent) throws IOException {
        System.out.println("Update button pressed");
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
