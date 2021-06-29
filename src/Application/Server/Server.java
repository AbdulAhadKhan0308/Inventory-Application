package Application.Server;

import Application.DataItem;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.PreparedStatement;

public class Server implements Runnable {

    private Connection dbconn;
    private Socket loginSocket;
    private static final String dbname="databaseusers";
    private static final String password="Mathema1ics!";
    private static final String connquery="?autoReconnect=true&useSSL=false";
    private static final String loginsearch = "SELECT username,password FROM databaseusers.userinfo WHERE username=? AND password=?;";
    private ServerSocket serverSocket;
    /*private static DataItem[] data;

    public static void main(String args[]) throws SQLException, IOException, ClassNotFoundException {
        ServerSocket serverSocket;
        Socket socket;
        System.out.println("Server started");
        try {
            serverSocket = new ServerSocket(5436);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        while (true) {
            try {
                socket = serverSocket.accept();
                Thread t = new Thread(new HandleClientSocket(socket));
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }*/
    @Override
    public void run() {
        System.out.println("Run invoked");
        
        try{
        dbconn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbname+connquery,"root",password);
        System.out.println("Connection estabished");
        
        serverSocket = new ServerSocket(5000);
        System.out.println("below serverSocket");
        
        
        
        while(true){
            System.out.println("waiting...");
            
            loginSocket = serverSocket.accept();
        System.out.println("loginSocket accepted");
        BufferedReader input = new BufferedReader(new InputStreamReader(loginSocket.getInputStream()));
        PrintWriter output = new PrintWriter(loginSocket.getOutputStream(),true);
        
        String login_input,username,userpassword;
        int i;
        
        login_input=input.readLine();
        System.out.println("here below input.readLine()");
        for(i=0;i<login_input.length();i++)
        if(login_input.charAt(i)=='$') break;
        
        if(i!=login_input.length()){
        username=login_input.substring(0, i);
        userpassword=login_input.substring(i+1,login_input.length());
        
        System.out.println(username+" and "+userpassword);
        
        PreparedStatement ps = dbconn.prepareStatement(loginsearch);
        ps.setString(1,username);
        ps.setInt(2,Integer.parseInt(userpassword));
        
        ResultSet result = ps.executeQuery();
        System.out.println("after executeQuery");
        
        if(result.next()) {
            output.println("jdbc:mysql://localhost:3306/"+dbname+connquery+"$root$"+password);
            
        }
        else output.println("0");
        }
        }
        
        /*loginSocket= serverSocket.accept();
        new LoggedClient(loginSocket).start();*/
           
        }
        catch(SQLException e){
            System.out.println("Connection not made");
        }
        catch(IOException e){
            System.out.println("ServerSocket not created");
        }
    }
}
