package views.tm;

public class CartTM {
    private String itemCode;
    private String description;
    private int qty;
    private double unitPrice;
    private double total;

    public CartTM() {
    }

    public CartTM(String itemCode, String description, int qty, double unitPrice, double total) {
        this.setItemCode(itemCode);
        this.setDescription(description);
        this.setQty(qty);
        this.setUnitPrice(unitPrice);
        this.setTotal(total);
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
