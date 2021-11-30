package dto;

public class OrderDetailDTO {
    private String orderId;
    private String itemCode;
    private int orderQty;
    private double UPrice;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(String orderId, String itemCode, int orderQty, double UPrice) {
        this.setOrderId(orderId);
        this.setItemCode(itemCode);
        this.setOrderQty(orderQty);
        this.setUPrice(UPrice);
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

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", orderQty=" + orderQty +
                ", UPrice=" + UPrice +
                '}';
    }
}
