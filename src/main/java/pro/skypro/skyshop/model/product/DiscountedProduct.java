package pro.skypro.skyshop.model.product;

import java.util.UUID;

public class DiscountedProduct extends Product {
    private final int basicPrice;
    private final double discount;

    public DiscountedProduct(UUID id, String name, int basicPrice, double discount) {
        super(id, name);
        if (basicPrice <= 0) {
            throw new IllegalArgumentException("Неверная цена!");
        }
        this.basicPrice = basicPrice;
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Неверная скидка!");
        }
        this.discount = discount;
    }

    public int getBasicPrice() {
        return basicPrice;
    }

    public double getDiscount() {
        return discount;
    }

    @Override
    public int getPrice() {
        return (int) (basicPrice * (1 - discount / 100));
    }

    @Override
    public Product clone() {
        return new DiscountedProduct(this.getId(), this.getName(), this.basicPrice, this.discount);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return getName() + ": " + getPrice() + "₽" + " (скидка " + discount + "%)";
    }
}
