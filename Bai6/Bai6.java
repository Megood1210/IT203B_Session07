package Bai6;

interface DiscountStrategy {
    double tinhGiam(double tongTien);
}

interface PaymentMethod {
    void thanhToan(double soTien);
}

interface NotificationService {
    void guiThongBao(String noiDung);
}

interface SalesChannelFactory {
    DiscountStrategy taoGiamGia();

    PaymentMethod taoThanhToan();

    NotificationService taoThongBao();
}

class WebsiteDiscount implements DiscountStrategy {
    public double tinhGiam(double tongTien) {
        System.out.println("Ap dung giam gia 10% cho website");
        return tongTien * 0.1;
    }
}

class MobileDiscount implements DiscountStrategy {
    public double tinhGiam(double tongTien) {
        System.out.println("Ap dung giam gia 15% cho lan dau mobile");
        return tongTien * 0.15;
    }
}

class PosDiscount implements DiscountStrategy {
    public double tinhGiam(double tongTien) {
        System.out.println("Ap dung giam gia thanh vien tai cua hang");
        return 50000;
    }
}

class CreditCardPayment implements PaymentMethod {
    public void thanhToan(double soTien) {
        System.out.println("Thanh toan the tin dung: " + soTien);
    }
}

class MomoPayment implements PaymentMethod {
    public void thanhToan(double soTien) {
        System.out.println("Thanh toan MoMo: " + soTien);
    }
}

class CashPayment implements PaymentMethod {
    public void thanhToan(double soTien) {
        System.out.println("Thanh toan tien mat tai POS: " + soTien);
    }
}

class EmailNotification implements NotificationService {
    public void guiThongBao(String noiDung) {
        System.out.println("Gui email: " + noiDung);
    }
}

class PushNotification implements NotificationService {
    public void guiThongBao(String noiDung) {
        System.out.println("Gui push notification: " + noiDung);
    }
}

class PrintNotification implements NotificationService {
    public void guiThongBao(String noiDung) {
        System.out.println("In hoa don giay: " + noiDung);
    }
}

class WebsiteFactory implements SalesChannelFactory {
    public DiscountStrategy taoGiamGia() {
        return new WebsiteDiscount();
    }

    public PaymentMethod taoThanhToan() {
        return new CreditCardPayment();
    }

    public NotificationService taoThongBao() {
        return new EmailNotification();
    }
}

class MobileFactory implements SalesChannelFactory {
    public DiscountStrategy taoGiamGia() {
        return new MobileDiscount();
    }

    public PaymentMethod taoThanhToan() {
        return new MomoPayment();
    }

    public NotificationService taoThongBao() {
        return new PushNotification();
    }
}

class PosFactory implements SalesChannelFactory {
    public DiscountStrategy taoGiamGia() {
        return new PosDiscount();
    }

    public PaymentMethod taoThanhToan() {
        return new CashPayment();
    }

    public NotificationService taoThongBao() {
        return new PrintNotification();
    }
}

class OrderService {
    private DiscountStrategy giamGia;
    private PaymentMethod thanhToan;
    private NotificationService thongBao;

    public OrderService(SalesChannelFactory factory) {
        this.giamGia = factory.taoGiamGia();
        this.thanhToan = factory.taoThanhToan();
        this.thongBao = factory.taoThongBao();
    }

    public void taoDonHang(String sanPham, double gia) {
        System.out.println("San pham: " + sanPham);
        double tienGiam = giamGia.tinhGiam(gia);
        double canTra = gia - tienGiam;
        thanhToan.thanhToan(canTra);
        thongBao.guiThongBao("Don hang thanh cong, so tien: " + canTra);
        System.out.println();
    }
}

public class Bai6 {
    public static void main(String[] args) {
        System.out.println("Kenh Website");
        OrderService websiteOrder = new OrderService(new WebsiteFactory());
        websiteOrder.taoDonHang("Laptop", 15000000);

        System.out.println("Kenh Mobile App");
        OrderService mobileOrder = new OrderService(new MobileFactory());
        mobileOrder.taoDonHang("Dien thoai", 8000000);

        System.out.println("Kenh POS");
        OrderService posOrder = new OrderService(new PosFactory());
        posOrder.taoDonHang("Tai nghe", 1200000);
    }
}