package dto;

public class ItemDetails {
    private String itemCode;
    private int qtyForSell;
    private double unitPrice;

    public ItemDetails() {
    }

    public ItemDetails(String itemCode,int  qtyForSell, double unitPrice) {
        this.setItemCode(itemCode);
        this.setQtyForSell(qtyForSell);
        this.setUnitPrice(unitPrice);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQtyForSell() {
        return qtyForSell;
    }

    public void setQtyForSell(int qtyForSell) {
        this.qtyForSell = qtyForSell;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
