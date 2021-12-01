package entity;

import javax.persistence.*;


/*@Entity
@AssociationOverrides({
        @AssociationOverride(name = "orderDetailId.order",joinColumns  = @JoinColumn),
        @AssociationOverride(name = "orderDetailId.item",joinColumns = @JoinColumn)
})*/
@Entity
public class OrderDetail {
    //private OrderDetailId orderDetailId = new OrderDetailId();
    @Id
    @GeneratedValue
    private String id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Item item;
    private int OrderQty;
    private double price;


    public OrderDetail() {
    }

    public OrderDetail(String id, Order order, Item item, int orderQty, double price) {
        this.setId(id);
        this.setOrder(order);
        this.setItem(item);
        OrderQty = orderQty;
        this.price = price;
    }

    public OrderDetail(Order order, Item item, int orderQty, double price) {
        this.order = order;
        this.item = item;
        OrderQty = orderQty;
        this.price = price;
    }

    /*@Transient
    public Order getOrder(){
        return getOrderDetailId().getOrder();
    }

    public void setOrder(Order order){
        getOrderDetailId().setOrder(order);
    }

    @Transient
    public Item getItem(){
        return getOrderDetailId().getItem();
    }*

    public void setItem(Item item){
        getOrderDetailId().setItem(item);
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @ManyToOne
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }



   /* public OrderDetailId getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(OrderDetailId orderDetailId) {
        this.orderDetailId = orderDetailId;
    }*/
}
