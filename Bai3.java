interface PaymentMethod {
    void process(double amount);
}

interface CODPayable extends PaymentMethod {
}

interface CardPayable extends PaymentMethod {
}

interface EWalletPayable extends PaymentMethod {
}

class CODPayment implements CODPayable {
    public void process(double amount) {
        System.out.println("Xử lý thanh toán COD: " + (long)amount + " - Thành công");
    }
}

class CreditCardPayment implements CardPayable {
    public void process(double amount) {
        System.out.println("Xử lý thanh toán thẻ tín dụng: " + (long)amount + " - Thành công");
    }
}

class MomoPayment implements EWalletPayable {

    public void process(double amount) {
        System.out.println("Xử lý thanh toán MoMo: " + (long)amount + " - Thành công");
    }
}

class PaymentProcessor {
    private PaymentMethod paymentMethod;

    public PaymentProcessor(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void execute(double amount) {
        paymentMethod.process(amount);
    }
}

public class Bai3 {
    public static void main(String[] args) {
        PaymentProcessor cod = new PaymentProcessor(new CODPayment());
        cod.execute(500000);

        PaymentProcessor card = new PaymentProcessor(new CreditCardPayment());
        card.execute(1000000);

        PaymentProcessor momo = new PaymentProcessor(new MomoPayment());
        momo.execute(750000);

        PaymentMethod payment = new MomoPayment();
        PaymentProcessor test = new PaymentProcessor(payment);
        test.execute(750000);

        System.out.println("Chương trình vẫn chạy đúng");
    }
}