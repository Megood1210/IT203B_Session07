import java.util.ArrayList;
import java.util.List;

class Order1 {
    private String id;

    public Order1(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

interface OrderRepository1 {
    void save(Order1 order);
    List<Order1> findAll();
}

class FileOrderRepository implements OrderRepository1 {
    private List<Order1> orders = new ArrayList<>();

    public void save(Order1 order) {
        orders.add(order);
        System.out.println("Lưu đơn hàng vào file: " + order.getId());
    }

    public List<Order1> findAll() {
        return orders;
    }
}

class DatabaseOrderRepository implements OrderRepository1 {
    private List<Order1> orders = new ArrayList<>();

    public void save(Order1 order) {
        orders.add(order);
        System.out.println("Lưu đơn hàng vào database: " + order.getId());
    }

    public List<Order1> findAll() {
        return orders;
    }
}

interface NotificationService {
    void send(String message, String recipient);
}

class EmailService1 implements NotificationService {
    public void send(String message, String recipient) {
        System.out.println("Gửi email: " + message);
    }
}

class SMSNotification implements NotificationService {
    public void send(String message, String recipient) {
        System.out.println("Gửi SMS: " + message);
    }
}

class OrderService {
    private OrderRepository1 repository;
    private NotificationService notificationService;

    public OrderService(OrderRepository1 repository, NotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    public void createOrder(String id, String recipient) {
        Order1 order = new Order1(id);

        repository.save(order);

        notificationService.send("Đơn hàng " + id + " đã được tạo", recipient);
    }
}

public class Bai4 {
    public static void main(String[] args) {
        OrderRepository1 repo1 = new FileOrderRepository();
        NotificationService notify1 = new EmailService1();

        OrderService service1 = new OrderService(repo1, notify1);
        service1.createOrder("ORD001", "a@example.com");

        System.out.println();

        OrderRepository1 repo2 = new DatabaseOrderRepository();
        NotificationService notify2 = new SMSNotification();

        OrderService service2 = new OrderService(repo2, notify2);
        service2.createOrder("ORD002", "0900000000");


        System.out.println("OrderService hoạt động với implementation mới");
    }
}