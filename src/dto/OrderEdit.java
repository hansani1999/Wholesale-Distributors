package dto;

public class OrderEdit {
    private String oId;
    private String itemCode;
    private int orderQty;

    public OrderEdit() {
    }

    public OrderEdit(String oId, String itemCode, int orderQty) {
        this.setoId(oId);
        this.setItemCode(itemCode);
        this.setOrderQty(orderQty);
    }


    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
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
}
