package win.pcdack.oscsmallclient.delegate.main.person.order;

/**
 * Created by pcdack on 17-10-29.
 *
 */

public class OrderItemVo {
    private long orderNo;
    private int productId;
    private String productName;
    private String productImage;
    private int currentUnitPrice;
    private int quantity;
    private int totalPrice;

    public long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(long orderNo) {
        this.orderNo = orderNo;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getCurrentUnitPrice() {
        return currentUnitPrice;
    }

    public void setCurrentUnitPrice(int currentUnitPrice) {
        this.currentUnitPrice = currentUnitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

}
