package pro.skypro.skyshop.model.product;

import java.util.UUID;

public class FixPriceProduct extends Product {
    private static final int FIX_PRICE = 990;

    public FixPriceProduct(UUID id, String name) {
        super(id, name);
    }

    @Override
    public int getPrice() {
        return FIX_PRICE;
    }

    @Override
    public Product clone() {
        return new FixPriceProduct(this.getId(), this.getName());
    }

    @Override
    public String toString() {
        return getName() + ": фикс. цена - " + FIX_PRICE + "₽";
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}
