package views.tm;


import javafx.scene.control.Button;

public class OrderItemTm {
    private String itemCode;
    private String description;
    private int orderQty;
    private double UPrice;
    private Button deleteButton;

    public OrderItemTm() {
    }

    public OrderItemTm(String itemCode, String description, int orderQty, double UPrice,Button deleteButton) {
        this.setItemCode(itemCode);
        this.setDescription(description);
        this.setOrderQty(orderQty);
        this.setUPrice(UPrice);
        this.setDeleteButton(deleteButton);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getUPrice() {
        return UPrice;
    }

    public void setUPrice(double UPrice) {
        this.UPrice = UPrice;
    }


    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    @Override
    public String toString() {
        return "OrderItemTm{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", orderQty=" + orderQty +
                ", UPrice=" + UPrice +
                ", deleteButton=" + deleteButton +
                '}';
    }
}
