package Bai5;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface DiscountStrategy {
    double applyDiscount(double amount);
}

class FixedDiscount implements DiscountStrategy {
    private double fixedAmount;
    public FixedDiscount(double fixedAmount) { this.fixedAmount = fixedAmount; }
    @Override
    public double applyDiscount(double amount) {
        return Math.min(fixedAmount, amount);
    }
}

class HolidayDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double amount) {
        return amount * 0.15;
    }
}

class PercentageDiscount implements DiscountStrategy {
    private double pct;
    public PercentageDiscount(double pct) { this.pct = pct; }
    @Override
    public double applyDiscount(double amt) { return amt * (pct / 100); }
}

interface PaymentMethod {
    void processPayment(double amount);
}

class CODPayment implements PaymentMethod {
    @Override
    public void processPayment(double amount) {
        System.out.println("Phương thức: Thanh toán khi nhận hàng (COD).");
    }
}

class CreditCardPayment implements PaymentMethod {
    public void processPayment(double amt) { System.out.println("Thanh toán " + amt + " qua thẻ tín dụng."); }
}

class MomoPayment implements PaymentMethod {
    @Override
    public void processPayment(double amount) {
        System.out.println("Đang kết nối cổng thanh toán Momo... Đã thanh toán " + amount);
    }
}

class VNPayPayment implements PaymentMethod {
    @Override
    public void processPayment(double amount) {
        System.out.println("Đang kết nối cổng thanh toán VNPay... Đã thanh toán " + amount);
    }
}

interface OrderRepository {
    void save(Order order); List<Order> getAll();
}

class FileOrderRepository implements OrderRepository {
    private final String FILE_PATH = "orders.txt";
    private List<Order> memoryCache = new ArrayList<>();

    @Override
    public void save(Order order) {
        memoryCache.add(order);

        try (FileWriter fw = new FileWriter(FILE_PATH, true);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("Order ID: " + order.orderId);
            pw.println("Customer: " + order.customer.name);
            pw.println("Amount: " + order.finalAmount);
            pw.println("--------------------------");

            System.out.println("Đã lưu đơn hàng " + order.orderId + " vào tệp tin orders.txt");

        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    @Override
    public List<Order> getAll() {
        return memoryCache;
    }

}

class DatabaseOrderRepository implements OrderRepository {
    private List<Order> orders = new ArrayList<>();

    @Override
    public void save(Order order) {
        orders.add(order);
        System.out.println("Đã lưu đơn hàng " + order.orderId + " vào hệ thống.");
    }

    @Override
    public List<Order> getAll() { return orders; }
}

interface NotificationService {
    void send(String message, Customer customer);
}

class EmailNotification implements NotificationService {
    public void send(String msg, Customer c) {
        System.out.println("Email gửi đến " + c.email + ": " + msg);
    }
}

class SMSNotification implements NotificationService {
    @Override
    public void send(String message, Customer customer) {
        System.out.println("SMS gửi đến " + customer.phone + ": " + message);
    }
}

class OrderService {
    private OrderRepository repo;
    private NotificationService notify;
    private InvoiceGenerator invoiceGen;

    public OrderService(OrderRepository repo, NotificationService notify, InvoiceGenerator invoiceGenerator) {
        this.repo = repo;
        this.notify = notify;
        this.invoiceGen = invoiceGenerator;
    }

    public void processOrder(Order order, DiscountStrategy discount, PaymentMethod payment) {
        double total = order.items.stream().mapToDouble(OrderItem::getSubTotal).sum();
        double discountAmt = (discount != null) ? discount.applyDiscount(total) : 0;

        order.totalBeforeDiscount = total;
        order.finalAmount = total - discountAmt;

        invoiceGen.generate(order, discountAmt);

        payment.processPayment(order.finalAmount);
        repo.save(order);
        notify.send("Đơn hàng " + order.orderId + " thành công!", order.customer);
    }
}

class InvoiceGenerator {
    public void generate(Order order, double discountAmount) {
        System.out.println("\n=== HÓA ĐƠN ===");
        System.out.println("Khách hàng: " + order.customer.name);
        for (OrderItem item : order.items) {
            System.out.printf("%s - Số lượng: %d - Đơn giá: %.0f - Thành tiền: %.0f\n",
                    item.product.name, item.quantity, item.product.price, item.getSubTotal());
        }
        System.out.println("---------------------------");
        System.out.printf("Tổng tiền: %.0f\n", order.totalBeforeDiscount);
        System.out.printf("Giảm giá: %.0f\n", discountAmount);
        System.out.printf("Cần thanh toán: %.0f\n", order.finalAmount);
        System.out.println("===========================\n");
    }
}

class Customer {
    public String name;
    public String email;
    public String phone;
    public Customer(String name, String email, String phone) {
        this.name = name; this.email = email; this.phone = phone;
    }
}

class Order {
    public String orderId;
    public Customer customer;
    public List<OrderItem> items = new ArrayList<>();
    public double totalBeforeDiscount;
    public double finalAmount;

    public Order(String orderId, Customer customer) {
        this.orderId = orderId; this.customer = customer;
    }
}

class OrderItem {
    public Product product;
    public int quantity;
    public OrderItem(Product product, int quantity) {
        this.product = product; this.quantity = quantity;
    }
    public double getSubTotal() { return product.price * quantity; }
}


class Product {
    String id;
    public String name;
    public double price;
    public Product(String id, String name, double price) {
        this.id = id; this.name = name; this.price = price;
    }
}

public class Bai5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OrderService orderService = new OrderService(new FileOrderRepository(),new SMSNotification(),new InvoiceGenerator());

        List<Product> products = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();

        System.out.println("--- DEMO TẠO ĐƠN HÀNG ---");
        Product p1 = new Product("SP01", "Laptop", 15000000);
        Customer c1 = new Customer("Nguyễn Văn A", "a@example.com", "0123");

        Order myOrder = new Order("ORD001", c1);
        myOrder.items.add(new OrderItem(p1, 1));

        DiscountStrategy tenPercent = new PercentageDiscount(10);
        PaymentMethod creditCard = new CreditCardPayment();

        System.out.println("=== HÓA ĐƠN ===");
        orderService.processOrder(myOrder, tenPercent, creditCard);

        System.out.println("\nTổng thanh toán thực tế: " + myOrder.finalAmount);
    }
}