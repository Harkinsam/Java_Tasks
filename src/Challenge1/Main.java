package Challenge1;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        SuperStore superStore = new SuperStore();
        superStore.addStock();
        superStore.upDateStock("Fruit","WaterMelon fruit",34,10.2);
        User user = new User(new Wallet(100.0));
        Order order1 = new Order(user,"Chocolate Chip",10, LocalDate.now().plusDays(4));
        Order order2 = new Order(user,"Carrot",5, LocalDate.now().plusDays(5));
        Order order3 = new Order(user,"Potato Chips",2, LocalDate.now().plusDays(10));
        Order order4 = new Order(user,"Whole Wheat",3, LocalDate.now().plusDays(3));
        Order order5 = new Order(user,"WaterMelon fruit",3, LocalDate.now().plusDays(6));
        Order order6 = new Order(user,"WaterMelon fruit",3, LocalDate.now().plusDays(2));


        superStore.addOrder(order1);
        superStore.addOrder(order2);
        superStore.addOrder(order5);
        superStore.addOrder(order6);
//                superStore.processOrders();

//                superStore.sortOrdersByDeliveryDate();

        superStore.createProcurementList();
        superStore.stockList();
    }
}
