package pro.skypro.skyshop.model.service;

import org.springframework.stereotype.Service;
import pro.skypro.skyshop.model.article.Article;
import pro.skypro.skyshop.model.product.DiscountedProduct;
import pro.skypro.skyshop.model.product.FixPriceProduct;
import pro.skypro.skyshop.model.product.Product;
import pro.skypro.skyshop.model.search.Searchable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StorageService {
    private final Map<UUID, Product> storageProducts;
    private final Map<UUID, Article> storageArticles;

    public StorageService() {
        this.storageProducts = new HashMap<>();
        this.storageArticles = new HashMap<>();
        initializeTestData();
    }

    // Методы для получения коллекций (не мап!)
    public Collection<Product> getAllProducts() {
        return storageProducts.values();
    }

    public Collection<Article> getAllArticles() {
        return storageArticles.values();
    }

    // Метод возвращающий все Searchable-объекты
    public Collection<Searchable> getAllSearchables() {
        return Stream.concat(
                storageProducts.values().stream(),
                storageArticles.values().stream()
        ).collect(Collectors.toList());
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(storageProducts.get(id));
    }

    private void initializeTestData() {
        // Создаём продукты
        Product product1 = new DiscountedProduct(UUID.randomUUID(), "Куртка", 14900, 20);
        Product product2 = new FixPriceProduct(UUID.randomUUID(), "Набор кухонных ножей");

        storageProducts.put(product1.getId(), product1);
        storageProducts.put(product2.getId(), product2);

        // Создаем статьи
        Article article1 = new Article(UUID.randomUUID(),
                "Описание куртки",
                "Куртка кожаная, с металлическими вставками, заклёпками, цепями, шипами. " +
                        "Самое то для поклонников тяжёлой музыки..."
        );

        Article article2 = new Article(UUID.randomUUID(),
                "Комплектация набора",
                "*Список ножей входящих в набор, описание, характеристики...*"
        );

        storageArticles.put(article1.getId(), article1);
        storageArticles.put(article2.getId(), article2);
    }
}
