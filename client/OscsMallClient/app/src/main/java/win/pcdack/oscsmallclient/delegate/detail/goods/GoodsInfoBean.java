package win.pcdack.oscsmallclient.delegate.detail.goods;

/**
 * Created by pcdack on 17-11-26.
 *
 */

public class GoodsInfoBean {
    private String name;
    private int price;
    private int stock;
    private int status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
