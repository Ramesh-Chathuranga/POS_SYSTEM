package lk.pos.fx.shop.model;



public class OrderDetailDTO {
   private String orderId;
   private String itemcode;
   private String description;
   private int qty;
   private double unitPrice;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(String orderId, String itemcode, String description, int qty, double unitPrice) {
        this.setOrderId(orderId);
        this.setItemcode(itemcode);
        this.setDescription(description);
        this.setQty(qty);
        this.setUnitPrice(unitPrice);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
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

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "orderId='" + orderId + '\'' +
                ", itemcode='" + itemcode + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
