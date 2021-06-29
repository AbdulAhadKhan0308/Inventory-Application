package Application.Client;

import javafx.application.Application;
import javafx.application.Platform;
import Application.Server.Server;
public class StartApplication {

    public static void main(String[] args) {
        
        Server server = new Server();
        Thread serverthread = new Thread(server);
        serverthread.start();
        Application.launch(Initiator.class,args);
    }
    public static void logout(){
        Platform.exit();
    }
}
