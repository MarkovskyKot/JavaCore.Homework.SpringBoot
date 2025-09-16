package pro.skypro.skyshop.model.basket;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProductBasket {
    private final Map<UUID, Integer> productBasket;

    public ProductBasket() {
        this.productBasket = new HashMap<>();
    }

    public void addProduct(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null!");
        }
        productBasket.merge(id, 1, Integer::sum);
    }

    public Map<UUID, Integer> getProductBasket() {
        return Collections.unmodifiableMap(productBasket);
    }
}
