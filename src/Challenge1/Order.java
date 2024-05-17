package Challenge1;

import java.time.LocalDate;

public class Order {
    private User user;
    private String productName;
    private int quantity;
    private LocalDate deliveryDate;
    private LocalDate procurementDay;

    public Order(User user,String productName, int quantity, LocalDate deliveryDate) {
        this.user = user;
        this.productName = productName;
        this.quantity = quantity;
        this.deliveryDate = deliveryDate;

    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public User user() {
        return user;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }
    public LocalDate getProcurementDay() {
        return procurementDay;
    }

    public void setProcurementDay(LocalDate procurementDay) {
        this.procurementDay = procurementDay;
    }

    @Override
    public String toString() {
        return "Order{" +
                "productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", deliveryDate=" + deliveryDate +
                '}';
    }
}

