package Challenge1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class SuperStore {
    private List<Product> productList;
    private PriorityQueue<Order> orderQueue;


    public SuperStore() {
        this.productList = new ArrayList<>();
        this.orderQueue = new PriorityQueue<>((order1, order2) -> {
            int quantity1 = order1.getQuantity();
            int quantity2 = order2.getQuantity();
            return Integer.compare(quantity1, quantity2);
        });
    }


    public List addStock(){
        try(BufferedReader reader = new BufferedReader(new FileReader("products_data.txt"))){
            String line = "";
            while((line = reader.readLine())!= null){
                String[] input = line.split(",");
                String category = input[0].toLowerCase();
                String name = input[1].toLowerCase();
                int quantity = Integer.parseInt(input[2]);
                double price = Double.parseDouble(input[3]);
                if(findProduct(name) >= 0){
                    Product product = productList.get(findProduct(name));
                    product.adjustStock(quantity);
                }else{
                    productList.add(new Product(category,name,quantity,price));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Product list populated successfully. Total products: " + productList.size());
        return productList;
    }

    public synchronized void addOrder(Order order) {
        // Check if the customer has reached the maximum of four deliveries for the month
        int monthlyDeliveries = getMonthlyDeliveriesForCustomer(order.user(), LocalDate.now());
        if (monthlyDeliveries >=4) {
            System.out.println("You have reached the maximum of four deliveries for this month.");
            return; // Exit the method without adding the order to the queue
        }
        orderQueue.offer(order);
    }

    private int getMonthlyDeliveriesForCustomer(User user, LocalDate currentDate) {

        int monthlyDeliveries = 0;
        for (Order order : orderQueue) {
            if (order.user().equals(user) && isSameMonth(order.getDeliveryDate(), currentDate)) {
                monthlyDeliveries++;
            }
        }
        return monthlyDeliveries;
    }

    private boolean isSameMonth(LocalDate date1, LocalDate date2) {
        // Check if two dates are in the same month
        return date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth();
    }


    public synchronized void processOrders() {
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.poll();
            String productName = order.getProductName();
            int quantity = order.getQuantity();
            User user = order.user();
            int index = findProduct(productName);
            if (index >= 0) {
                Product product = productList.get(index);
                int availableQuantity = product.getQuantity();
                if (quantity <= availableQuantity) {
                    double totalPrice = product.price() * quantity;
                    if (user.getWalletBalance() >= totalPrice) { // Check if user can afford the order
                        product.adjustStock(-quantity);
                        System.out.println("Order processed: " + productName + " (" + quantity + ")");
                        user.deductFundsFromWallet(totalPrice); // Deduct funds from user's wallet
                    } else {
                        System.out.println("Insufficient funds to place order: " + productName + " (" + quantity + ")");
                    }
                } else {
                    System.out.println("Insufficient stock for order: " + productName + " (" + quantity + ")");
                }
            } else {
                System.out.println("Product not found: " + productName);
            }
        }
    }

    public void upDateStock(String category,String productName, int quantity,double price) {
        int index  = findProduct(productName);
        if (index >=0) {
            Product product = productList.get(index);
            product.adjustStock(quantity);
        } else {
            productList.add(new Product(category.toLowerCase(), productName.toLowerCase(), quantity, price));
            System.out.println("Successful");

        }
    }
    private int findProduct(String productName){
        for(Product product1 : productList){
            if(product1.name().equalsIgnoreCase(productName)){
                return productList.indexOf(product1);
            }
        }
        return -1;
    }

//    public void sortOrdersByDeliveryDate() {
//        List<Order> sortedOrders = new ArrayList<>(orderQueue);
//        sortedOrders.sort(Comparator.comparing(Order::getDeliveryDate));
//        for (Order order : sortedOrders) {
//            System.out.println(order);
//        }
//    }

    public void stockList(){
        for(Product product:productList){
            System.out.println(product);
        }
    }
    public int findProducts(String productName){
        for(Product product1 : productList){
            if(product1.name().equalsIgnoreCase(productName)){
                int product = productList.indexOf(product1);
                return productList.get(product).getQuantity();
            }
        }
        return 0;
    }

    public void createProcurementList() {
        List<Order> procurementList = new ArrayList<>(orderQueue);
        List<Order> perishableOrders = new ArrayList<>();
        List<Order> nonPerishableOrders = new ArrayList<>();

        for (Order order : procurementList) {
            if (isPerishableProduct(order.getProductName())) {
                perishableOrders.add(order);
            } else {
                nonPerishableOrders.add(order);
            }
        }

        // Calculate and attach procurement day for perishable orders
        for (Order perishableOrder : perishableOrders) {
            LocalDate deliveryDate = perishableOrder.getDeliveryDate();
            LocalDate procurementDay = deliveryDate.minusDays(1); // Attach procurement day as 1 day before delivery
            perishableOrder.setProcurementDay(procurementDay);
        }

        // Sort perishable orders by delivery date (ascending order)
        perishableOrders.sort(Comparator.comparing(Order::getDeliveryDate));

        System.out.println("Procurement List:");
        System.out.println("Non-Perishable Orders:");
        for (Order order : nonPerishableOrders) {
            System.out.println("Product: " + order.getProductName() + ", Delivery Date: " + order.getDeliveryDate());
        }

        System.out.println("Perishable Orders:");
        for (Order order : perishableOrders) {
            System.out.println("Product: " + order.getProductName() + ", Delivery Date: " + order.getDeliveryDate() + ", Procurement Date: " + order.getDeliveryDate().minusDays(1));
        }
    }



    private boolean isPerishableProduct(String productName) {
        // Simulated logic to determine if a product is perishable based on its name
        String[] perishableKeywords = {"fruit", "vegetable", "perishable"};
        for (String keyword : perishableKeywords) {
            if (productName.toLowerCase().contains(keyword)) {
                return true;
            }
        }
        return false;
    }


}
