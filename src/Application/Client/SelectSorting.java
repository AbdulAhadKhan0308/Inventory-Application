package Application.Client;

import Application.DataItem;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SelectSorting {
    public Button priceSortButton;
    public Button quantitySortButton;


    public void priceSortListener(ActionEvent actionEvent) {
        try {
            Socket socket = new Socket("localhost", 5436);
            System.out.println("Client created.");
            new SelectSorting().sendMessage(socket,"Price");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                        DataItem[] data = (DataItem[]) objectInputStream.readObject();
                        System.out.println("Message Recieved");
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Parent root = null;
                                Stage stage = (Stage) priceSortButton.getScene().getWindow();
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ContentDisplayGUI.fxml"));
                                try {
                                    root = (Parent) fxmlLoader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                ContentDisplayGUI contentDisplayGUI = fxmlLoader.<ContentDisplayGUI>getController();
                                int i=0;
                                String s="";
                                while(i< data.length){
                                    s=s+data[i++].toString()+"\n";
                                }
                                contentDisplayGUI.contentTextArea.setText(s);
                                stage.setTitle("Search result");
                                stage.setScene(new Scene(root, 600, 200));
                                stage.show();
                            }
                        });
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void quantitySortListener(ActionEvent actionEvent) {
        try {
            Socket socket = new Socket("localhost", 5436);
            System.out.println("Client created.");
            new SelectSorting().sendMessage(socket,"Quantity");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                        DataItem[] data = (DataItem[]) objectInputStream.readObject();
                        System.out.println("Message Recieved");
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Parent root = null;
                                Stage stage = (Stage) priceSortButton.getScene().getWindow();
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ContentDisplayGUI.fxml"));
                                try {
                                    root = (Parent) fxmlLoader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                ContentDisplayGUI contentDisplayGUI = fxmlLoader.<ContentDisplayGUI>getController();
                                int i=0;
                                String s="";
                                while(i< data.length){
                                    s=s+data[i++].toString()+"\n";
                                }
                                contentDisplayGUI.contentTextArea.setText(s);
                                stage.setTitle("Search result");
                                stage.setScene(new Scene(root, 600, 200));
                                stage.show();
                            }
                        });
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Socket socket,String choice) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject("Sort");
        objectOutputStream.flush();
        objectOutputStream.writeObject(choice);
        objectOutputStream.flush();
    }
}
