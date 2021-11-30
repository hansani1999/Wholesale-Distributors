package entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrderDetail {
    @Id
    private String orderId;
    private String itemCode;
    private int OrderQty;
    private double price;


    public OrderDetail() {
    }

    public OrderDetail(String orderId, String itemCode, int orderQty, double price) {
        this.setOrderId(orderId);
        this.setItemCode(itemCode);
        setOrderQty(orderQty);
        this.setPrice(price);
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getOrderQty() {
        return OrderQty;
    }

    public void setOrderQty(int orderQty) {
        OrderQty = orderQty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", OrderQty=" + OrderQty +
                ", price=" + price +
                '}';
    }
}
