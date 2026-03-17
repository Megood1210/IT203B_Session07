interface DiscountStrategy {
    double applyDiscount(double totalAmount);
}

class PercentageDiscount implements DiscountStrategy {
    private double percent;

    public PercentageDiscount(double percent) {
        this.percent = percent;
    }

    public double applyDiscount(double totalAmount) {
        return totalAmount - (totalAmount * percent / 100);
    }
}

class FixedDiscount implements DiscountStrategy {
    private double amount;

    public FixedDiscount(double amount) {
        this.amount = amount;
    }

    public double applyDiscount(double totalAmount) {
        return totalAmount - amount;
    }
}

class NoDiscount implements DiscountStrategy {
    public double applyDiscount(double totalAmount) {
        return totalAmount;
    }
}

class OrderCalculator1 {
    private DiscountStrategy discountStrategy;

    public OrderCalculator1(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double calculate(double totalAmount) {
        return discountStrategy.applyDiscount(totalAmount);
    }
}

class HolidayDiscount implements DiscountStrategy {
    public double applyDiscount(double totalAmount) {
        return totalAmount - (totalAmount * 15 / 100);
    }
}

public class Bai2 {
    public static void main(String[] args) {
        double total = 1000000;

        OrderCalculator1 order1 = new OrderCalculator1(new PercentageDiscount(10));
        System.out.println("Số tiền sau giảm: " + (long) order1.calculate(total));

        OrderCalculator1 order2 = new OrderCalculator1(new FixedDiscount(50000));
        System.out.println("Số tiền sau giảm: " + (long) order2.calculate(total));

        OrderCalculator1 order3 = new OrderCalculator1(new NoDiscount());
        System.out.println("Số tiền sau giảm: " + (long) order3.calculate(total));

        OrderCalculator1 order4 = new OrderCalculator1(new HolidayDiscount());
        System.out.println("Số tiền sau giảm: " + (long) order4.calculate(total));
    }
}