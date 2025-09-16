package pro.skypro.skyshop.model.controller;

import org.springframework.web.bind.annotation.*;
import pro.skypro.skyshop.model.article.Article;
import pro.skypro.skyshop.model.basket.UserBasket;
import pro.skypro.skyshop.model.product.Product;
import pro.skypro.skyshop.model.search.SearchResult;
import pro.skypro.skyshop.model.service.BasketService;
import pro.skypro.skyshop.model.service.SearchService;
import pro.skypro.skyshop.model.service.StorageService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shop")
public class ShopController {
    private final StorageService storageService;
    private final SearchService searchService;
    private final BasketService basketService;

    public ShopController(StorageService storageService, SearchService searchService, BasketService basketService) {
        this.storageService = storageService;
        this.searchService = searchService;
        this.basketService = basketService;
    }

    @GetMapping
    public String showGreeting() {
        return "Welcome to the SkyShop!";
    }

    @GetMapping("/products")
    public Collection<Product> getAllProducts() {
        return new ArrayList<>(storageService.getAllProducts());
    }

    @GetMapping("/articles")
    public Collection<Article> getAllArticles() {
        return new ArrayList<>(storageService.getAllArticles());
    }

    @GetMapping("/search")
    public List<SearchResult> search(@RequestParam String pattern) {
        return searchService.search(pattern);
    }

    @GetMapping("/basket/{id}")
    public String addProduct(@PathVariable("id") UUID id) {
        basketService.addProduct(id);
        return "*Продукт успешно добавлен*";
    }

    @GetMapping("/basket")
    public UserBasket getUserBasket() {
        return basketService.getUserBasket();
    }
}
