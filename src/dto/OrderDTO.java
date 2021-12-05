package dto;

import java.util.ArrayList;

public class OrderDTO {
    private String orderId;
    private String customerId;
    private String orderDate;
    private String time;
    private Double total;
    private Double discount;
    private ArrayList<ItemDetails> items;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, String customerId, String orderDate, String time, Double total, Double discount, ArrayList<ItemDetails> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.time = time;
        this.total = total;
        this.discount = discount;
        this.items = items;
    }

    public OrderDTO(String orderId, String customerId, String orderDate, String time, Double total, Double discount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.time = time;
        this.total = total;
        this.discount = discount;
    }

    public OrderDTO(String orderId, String orderDate, String time, Double total, Double discount, ArrayList<ItemDetails> items) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.time = time;
        this.total = total;
        this.discount = discount;
        this.items = items;
    }

    public OrderDTO(String orderId, String orderDate, String time, Double total, Double discount) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.time = time;
        this.total = total;
        this.discount = discount;
    }

    /*public OrderDTO(String orderId, Double total, Double discount) {
        this.orderId = orderId;
        this.total = total;
        this.discount = discount;
    }*/

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public ArrayList<ItemDetails> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemDetails> items) {
        this.items = items;
    }
}
