/*package Application.Client;

import Application.DataItem;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class SearchChoice {

    public Button idSearchButton;
    public Button nameSearchButton;
    public Label choiceLabel;
    public TextField idSearchTextField;
    public TextField nameSearchTextField;
    public Label functionLabel;

    public void idSearchListener(ActionEvent actionEvent) {
        try {
            Socket socket = new Socket("localhost", 5436);
            System.out.println("Client created.");
            new SearchChoice().sendMessage(socket,functionLabel.getText(),"ID",idSearchTextField.getText());
            if(functionLabel.getText().equals("Search-Window")) {
                System.out.println("Going into the thread");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("recieving Message");
                        try {
                            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                            DataItem data = (DataItem) objectInputStream.readObject();
                            System.out.println("Message Recieved");
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Parent root = null;
                                    Stage stage = (Stage) idSearchButton.getScene().getWindow();
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SearchResult.fxml"));
                                    try {
                                        root = (Parent) fxmlLoader.load();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    SearchResult searchResult = fxmlLoader.<SearchResult>getController();
                                    searchResult.brandLabel.setText("" + data.getBrand());
                                    searchResult.priceLabel.setText("" + data.getPrice());
                                    searchResult.productIDLabel.setText("" + data.getProductID());
                                    searchResult.productNameLabel.setText("" + data.getProductName());
                                    searchResult.quantityLabel.setText("" + data.getQuantity());
                                    stage.setTitle("Search result");
                                    stage.setScene(new Scene(root, 400, 200));
                                    stage.show();
                                }
                            });
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            else {
                System.out.println("Going into the thread");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("recieving Message");
                        try {
                            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                            String result=(String)objectInputStream.readObject();
                            if(result.equals("Success")) {
                                System.out.println("Message Recieved");
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Parent root = null;
                                        Stage stage = (Stage) nameSearchButton.getScene().getWindow();
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
                                        Stage stage = (Stage) nameSearchButton.getScene().getWindow();
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Socket socket,String function,String type,String input) throws IOException{
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(function);
        objectOutputStream.flush();
        objectOutputStream.writeObject(type);
        objectOutputStream.flush();
        objectOutputStream.writeObject(input);
        objectOutputStream.flush();
    }


    public void nameSearchListener(ActionEvent actionEvent) {
        try {
            Socket socket = new Socket("localhost", 5436);
            System.out.println("Client created.");
            new SearchChoice().sendMessage(socket,functionLabel.getText(),"name",nameSearchTextField.getText());
            if(functionLabel.getText().equals("Search-Window")) {
                System.out.println("Going into the thread");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("recieving Message");
                        try {
                            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                            DataItem data = (DataItem) objectInputStream.readObject();
                            System.out.println("Message Recieved");
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Parent root = null;
                                    Stage stage = (Stage) nameSearchButton.getScene().getWindow();
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SearchResult.fxml"));
                                    try {
                                        root = (Parent) fxmlLoader.load();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    SearchResult searchResult = fxmlLoader.<SearchResult>getController();
                                    searchResult.brandLabel.setText("" + data.getBrand());
                                    searchResult.priceLabel.setText("" + data.getPrice());
                                    searchResult.productIDLabel.setText("" + data.getProductID());
                                    searchResult.productNameLabel.setText("" + data.getProductName());
                                    searchResult.quantityLabel.setText("" + data.getQuantity());
                                    stage.setTitle("Search result");
                                    stage.setScene(new Scene(root, 400, 200));
                                    stage.show();
                                }
                            });
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            else {
                System.out.println("Going into the thread");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("recieving Message");
                        try {
                            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                            String result=(String)objectInputStream.readObject();
                            if(result.equals("Success")) {
                                System.out.println("Message Recieved");
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Parent root = null;
                                        Stage stage = (Stage) nameSearchButton.getScene().getWindow();
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
                            else{
                                System.out.println("Message Recieved");
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Parent root = null;
                                        Stage stage = (Stage) nameSearchButton.getScene().getWindow();
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/