package dto;

public class CustomDTO {
    private String itemCode;
    private int itemExpenditure;
    private String description;
    private int orderQty;
    private double UPrice;

    public CustomDTO() {
    }

    public CustomDTO(String itemCode, int itemExpenditure) {
        this.itemCode = itemCode;
        this.itemExpenditure = itemExpenditure;
    }

    public CustomDTO(String itemCode, String description, int orderQty, double UPrice) {
        this.setItemCode(itemCode);
        this.setDescription(description);
        this.setOrderQty(orderQty);
        this.setUPrice(UPrice);
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

    public int getItemExpenditure() {
        return itemExpenditure;
    }

    public void setItemExpenditure(int itemExpenditure) {
        this.itemExpenditure = itemExpenditure;
    }

    @Override
    public String toString() {
        return "CustomDTO{" +
                "itemCode='" + itemCode + '\'' +
                ", itemExpenditure=" + itemExpenditure +
                '}';
    }
}
