package pro.skypro.skyshop.model.product;

import java.util.UUID;

public class SimpleProduct extends Product {
    private final int price;

    public SimpleProduct(UUID id, String name, int price) {
        super(id, name);
        if (price <= 0) {
            throw new IllegalArgumentException("Неверная цена!");
        }
        this.price = price;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public Product clone() {
        return new SimpleProduct(this.getId(), this.getName(), this.price);
    }

    @Override
    public String toString() {
        return getName() + ": " + price + "₽";
    }

    @Override
    public boolean isSpecial() {
        return false;
    }
}
