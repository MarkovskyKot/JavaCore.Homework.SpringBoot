package pro.skypro.skyshop.model.product;

import pro.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public abstract class Product implements Searchable {
    private final UUID id;
    private final String name;

    public Product(UUID id, String name) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null!");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Некорректное название!");
        }
        this.id = id;
        this.name = name;
    }

    @Override
    public String getSearchTerm() {
        return getName();
    }

    @Override
    public String getContentType() {
        return "PRODUCT";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public abstract int getPrice();

    public abstract Product clone();

    public abstract boolean isSpecial();

    public abstract String toString();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name.toLowerCase(), product.name.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name.toLowerCase());
    }
}
