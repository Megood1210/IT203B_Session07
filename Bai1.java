import java.util.ArrayList;
import java.util.List;

class Product {
    private String id;
    private String name;
    private double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }
}

class Customer {
    private String name;
    private String email;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
        System.out.println("Đã thêm khách hàng");
    }

    public String getEmail() {
        return email;
    }
}

class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}

class Order {
    private String orderId;
    private Customer customer;
    private List<OrderItem> items;
    private double total;

    public Order(String orderId, Customer customer, List<OrderItem> items) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = items;
        System.out.println("Đơn hàng " + orderId + " được tạo");
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }
}

class OrderCalculator {
    public double calculateTotal(Order order) {
        double total = 0;

        for (OrderItem item : order.getItems()) {
            total += item.getTotalPrice();
        }

        System.out.println("Tổng tiền: " + (long) total);
        return total;
    }
}

class OrderRepository {
    public void save(Order order) {
        System.out.println("Đã lưu đơn hàng " + order.getOrderId());
    }
}

class EmailService {
    public void sendEmail(Order order) {
        System.out.println("Đã gửi email đến "+ order.getCustomer().getEmail()+ ": Đơn hàng " + order.getOrderId()+ " đã được tạo");
    }
}

public class Bai1 {
    public static void main(String[] args) {
        Product p1 = new Product("SP01", "Laptop", 15000000);
        Product p2 = new Product("SP02", "Chuột", 300000);
        System.out.println("Đã thêm sản phẩm SP01, SP02");

        Customer customer = new Customer("Nguyễn Văn A","a@example.com");

        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(p1, 1));
        items.add(new OrderItem(p2, 2));

        Order order = new Order("ORD001", customer, items);

        OrderCalculator calculator = new OrderCalculator();
        double total = calculator.calculateTotal(order);
        order.setTotal(total);

        OrderRepository repository = new OrderRepository();
        repository.save(order);

        EmailService email = new EmailService();
        email.sendEmail(order);
    }
}