package pro.skypro.skyshop.model.service;

import org.springframework.stereotype.Service;
import pro.skypro.skyshop.model.basket.BasketItem;
import pro.skypro.skyshop.model.basket.ProductBasket;
import pro.skypro.skyshop.model.basket.UserBasket;
import pro.skypro.skyshop.model.exception.NoSuchProductException;
import pro.skypro.skyshop.model.product.Product;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BasketService {
    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public void addProduct(UUID id) {
        Product product = storageService.getProductById(id).orElseThrow(() -> new NoSuchProductException("Product not found"));
        productBasket.addProduct(id);
    }

    public UserBasket getUserBasket() {
        Map<UUID, Integer> basketItems = productBasket.getProductBasket();

        List<BasketItem> items = basketItems.entrySet().stream()
                .map(entry -> {
                    Product product = storageService.getProductById(entry.getKey())
                            .orElseThrow(() -> new NoSuchProductException("Product not found"));
                    return new BasketItem(product, entry.getValue());
                })
                .collect(Collectors.toList());

        return new UserBasket(items);
    }
}
