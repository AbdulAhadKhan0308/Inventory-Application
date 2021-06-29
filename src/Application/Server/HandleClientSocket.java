/*package Application.Server;

import Application.DataItem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;

public class HandleClientSocket implements Runnable {
    private Application.DataItem[] data;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public HandleClientSocket(Socket socket) {
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void storeData() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/foodpile";
        Connection connection = DriverManager.getConnection(url, "root", "TetraM0s");
        String query1 = "Select count(*) FROM INVENTORY";
        PreparedStatement preStat = connection.prepareStatement(query1);
        String query2 = "SELECT * FROM INVENTORY;";
        ResultSet result = preStat.executeQuery();
        int i = 0;
        while (result.next()) {
            i = result.getInt("count(*)");
        }
        data = new Application.DataItem[i];
        i = 0;
        preStat = connection.prepareStatement(query2);
        result = preStat.executeQuery();

        while (result.next()) {
            data[i] = new Application.DataItem();
            data[i].setDataItem(result.getString("productName"), result.getString("brand"), result.getString("productID"), result.getInt("quantity"), result.getDouble("price"));
            i++;
        }
    }

    @Override
    public void run() {
        String function;
        String key="";
        String input="";
        while (true) {
            try {
                function=(String)objectInputStream.readObject();
                if(!function.equals("Insertion")) {
                    key = (String) objectInputStream.readObject();
                    if (!function.equals("Sort")&&!function.equals("Update")) {
                        input = (String) objectInputStream.readObject();
                    }
                }
                System.out.println("Message received");
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            try {
                this.storeData();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            int i=0;
            switch (function) {
                case "Update" -> {
                    try {
                        if (update(key)) {
                            objectOutputStream.writeObject("Success");
                        } else {
                            objectOutputStream.writeObject("Failure");
                        }
                        objectOutputStream.flush();
                        System.out.println("Message sent");
                    } catch (IOException | SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                case "Search-Window" -> {
                    switch (key) {
                        case "ID" -> {
                            while (i < data.length) {
                                if (!data[i].getProductID().equals(input)) i++;
                                else break;
                            }
                        }
                        case "name" -> {
                            while (i < data.length) {
                                if (!data[i].getProductName().equals(input)) i++;
                                else break;
                            }
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + key);
                    }
                    try {
                        if (i < data.length) objectOutputStream.writeObject(data[i]);
                        else throw new IllegalStateException("Unexpected value " + input);
                        objectOutputStream.flush();
                        System.out.println("Message sent");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case "Deletion-Window" -> {
                    switch (key) {
                        case "ID" -> {
                            while (i < data.length) {
                                if (!data[i].getProductID().equals(input)) i++;
                                else try {
                                    deleteData(i);
                                    System.out.println("Sending Message");
                                    objectOutputStream.writeObject("Success");
                                    objectOutputStream.flush();
                                    System.out.println("Message sent");
                                    break;
                                } catch (ClassNotFoundException | SQLException | IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        case "name" -> {
                            while (i < data.length) {
                                if (!data[i].getProductName().equals(input)) i++;
                                else try {
                                    deleteData(i);
                                    System.out.println("Sending Message");
                                    objectOutputStream.writeObject("Success");
                                    objectOutputStream.flush();
                                    System.out.println("Message sent");
                                    break;
                                } catch (ClassNotFoundException | SQLException | IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + key);
                    }
                }
                case "Sort" -> {
                    sort(key);
                    try {
                        objectOutputStream.writeObject(data);
                        objectOutputStream.flush();
                        System.out.println("Message sent");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case "Insertion" -> {
                    Application.DataItem temp = new Application.DataItem();
                    try {
                        String name = (String) objectInputStream.readObject();
                        String id = (String) objectInputStream.readObject();
                        String brand = (String) objectInputStream.readObject();
                        String quantity = (String) objectInputStream.readObject();
                        String price = (String) objectInputStream.readObject();
                        temp.setDataItem(name, id, brand, Integer.parseInt(quantity), Double.parseDouble(price));
                        System.out.println(temp.toString() + "\n");
                        if (insertData(temp)) {
                            objectOutputStream.writeObject("Success");
                        } else {
                            objectOutputStream.writeObject("Failure");
                        }
                        objectOutputStream.flush();
                        System.out.println("Message sent");
                    } catch (IOException | ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + function);
            }
        }
    }

    private boolean update(String key) throws ClassNotFoundException, SQLException, IOException {
        int i;
        for(i=0;i< data.length;i++){
            if(data[i].getProductID().equals(key)){
                String name=(String)objectInputStream.readObject();
                String brand=(String)objectInputStream.readObject();
                String quantity=(String)objectInputStream.readObject();
                String price=(String)objectInputStream.readObject();
                if(name.length()==0)name=data[i].getProductName();
                if(brand.length()==0)brand=data[i].getBrand();
                if(quantity.length()==0)quantity=""+data[i].getQuantity();
                if(price.length()==0)price=""+data[i].getPrice();
                data[i].setDataItem(name,brand,key,Integer.parseInt(quantity),Double.parseDouble(price));
                break;
            }
        }
        if(i== data.length) return  false;
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/foodpile";
        Connection connection = DriverManager.getConnection(url, "root", "TetraM0s");
        String query="update inventory set ";
        query=query+"productName'"+data[i].getProductName()+"',";
        query=query+"brand='"+data[i].getBrand()+"',";
        query=query+"quantity="+data[i].getQuantity()+",";
        query=query+"price='"+data[i].getPrice()+" ";
        query=query+"where productID='"+key+";";
        PreparedStatement preStat = connection.prepareStatement(query);
        return preStat.executeUpdate() == 1;
    }

    private boolean insertData(Application.DataItem temp) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/foodpile";
        Connection connection = DriverManager.getConnection(url, "root", "TetraM0s");
        String query="insert into inventory (productName,productID,brand,quantity,price) values ('"+temp.getProductName()+"','"+temp.getProductID()+"','"+temp.getBrand()+"',"+temp.getQuantity()+","+temp.getPrice()+");";
        PreparedStatement preStat = connection.prepareStatement(query);
        return preStat.executeUpdate() == 1;
    }

    private void sort(String type) {
        int i,j;
        Application.DataItem temp;
        switch (type) {
            case "Price" -> {
                for (i = 1; i < data.length; i++) {
                    System.out.println(i);
                    temp = data[i];
                    for (j = i - 1; j >= 0; j--) {
                        if (temp.getPrice() < data[j].getPrice()) data[j + 1] = data[j];
                        else break;
                    }
                    data[j + 1] = temp;
                }
            }
            case "Quantity" -> {
                for (i = 1; i < data.length; i++) {
                    System.out.println(i);
                    temp = data[i];
                    for (j = i - 1; j >= 0; j--) {
                        if (temp.getQuantity() < data[j].getQuantity()) data[j + 1] = data[j];
                        else break;
                    }
                    data[j + 1] = temp;
                }
            }
        }
    }

    private void deleteData(int pos) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/foodpile";
        Connection connection = DriverManager.getConnection(url, "root", "TetraM0s");
        String query1 = "Delete FROM INVENTORY WHERE productID='"+data[pos].getProductID()+"';";
        PreparedStatement preStat = connection.prepareStatement(query1);
        System.out.println(""+preStat.executeUpdate());
        int size=data.length;
        Application.DataItem[] temp=new DataItem[size-1];
        int i;
        for (i=0;i< temp.length;i++){
            if(i<pos)temp[i]=data[i];
            else temp[i]=data[i+1];
        }
        data=temp;
    }
}*/
