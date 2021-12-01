package embedded;


import entity.Item;
import entity.Order;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;

@Embeddable
public class OrderDetailId implements Serializable {

    private Order order;
    private Item item;

    public OrderDetailId() {
    }

    public OrderDetailId(Order order, Item item) {
        this.setOrder(order);
        this.setItem(item);
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

    @Override
    public String toString() {
        return "OrderDetailId{" +
                "order=" + order +
                ", item=" + item +
                '}';
    }
}
