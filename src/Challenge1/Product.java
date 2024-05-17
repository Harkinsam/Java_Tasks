package Challenge1;

public class Product {
    private String category;
    private String name;
    private int quantity = 0;
    private double price;

    public Product(String category, String name, int quantity, double price) {
        this.category = category;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String category() {
        return category;
    }

    public String name() {
        return name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double price() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public  void adjustStock(int quantity) {
        int newQuantity = this.quantity + quantity;
        if(newQuantity >=0){
            this.quantity = newQuantity;
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
