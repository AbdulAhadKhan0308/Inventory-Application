package Application;

import java.io.Serializable;

public class DataItem implements Serializable{
    private String productName;
    private String brand;
    private String productID;
    private int quantity;
    //private int criticalquantity;
    //private int batchID;
    
    //private boolean isuniqueIDvalid;
    //serial number in machine barrel(individually ided),, screws ,bolts(not individually ided),, quantity decided by batches; 
    private double price;

    /*public void setDataItem(String productName,String brand,String productID,int quantity,double price){
        this.productName=productName;
        this.brand=brand;
        this.productID=productID;
        this.quantity=quantity;
        this.price=price;
    }

    public String getProductName(){
        return this.productName;
    }

    public String getBrand(){
        return this.brand;
    }

    public String getProductID(){
        return this.productID;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public double getPrice(){
        return this.price;
    }*/

    @Override
    public String toString() {
        return "DataItem{" +
                "productName='" + productName + '\'' +
                ", brand='" + brand + '\'' +
                ", productID='" + productID + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
