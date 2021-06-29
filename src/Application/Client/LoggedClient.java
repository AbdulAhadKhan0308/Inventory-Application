/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.Client;

import java.net.Socket;

/**
 *
 * @author ABDUL AHAD KHAN
 */
public class LoggedClient extends Thread {
    Socket socket;
    public LoggedClient(Socket socket){
        this.socket=socket;
        System.out.println("Client Connected");
    }
}
