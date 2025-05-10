package uts.isd.model;

import java.math.BigDecimal;

public class CartItem {
    private int deviceId;
    private String name;
    private BigDecimal price;
    private int quantity;

    public CartItem(int deviceId, String name, BigDecimal price) {
        this.deviceId = deviceId;
        this.name = name;
        this.price = price;
        this.quantity = 1;
    }

    public int getDeviceId() { return deviceId; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void incrementQuantity() { quantity++; }
    public BigDecimal getTotal() { return price.multiply(new BigDecimal(quantity)); }
}
