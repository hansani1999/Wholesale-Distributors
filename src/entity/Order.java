package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {
    @Id
    private String orderId;
    private String cId;
    private String orderDate;
    private String time;
    private double cost;
    private double discount;

    @ManyToOne
    private Customer customer;

    @OneToMany
    private List<OrderDetail> list = new ArrayList<>();

    public Order() {
    }

    public Order(String orderId, double cost, double discount) {
        this.orderId = orderId;
        this.cost = cost;
        this.discount = discount;
    }

    public Order(String orderId, String cId, String orderDate, String time, double cost, double discount) {
        this.setOrderId(orderId);
        this.setcId(cId);
        this.setOrderDate(orderDate);
        this.setTime(time);
        this.setCost(cost);
        this.setDiscount(discount);
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }


    public List<OrderDetail> getList() {
        return list;
    }

    public void setList(List<OrderDetail> list) {
        this.list = list;
    }
}
