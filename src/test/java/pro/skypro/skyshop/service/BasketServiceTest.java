package pro.skypro.skyshop.service;

import pro.skypro.skyshop.model.exception.NoSuchProductException;
import pro.skypro.skyshop.model.basket.BasketItem;
import pro.skypro.skyshop.model.basket.ProductBasket;
import pro.skypro.skyshop.model.basket.UserBasket;
import pro.skypro.skyshop.model.product.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.skypro.skyshop.model.service.BasketService;
import pro.skypro.skyshop.model.service.StorageService;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    @Test
    void addProductToBasket_WhenProductNotExists_ThrowsNoSuchProductException() {
        UUID productId = UUID.randomUUID();
        when(storageService.getProductById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class, () -> {
            basketService.addProduct(productId);
        });

        verify(storageService).getProductById(productId);
        verifyNoInteractions(productBasket);
    }

    @Test
    void addProductToBasket_WhenProductExists_CallsAddProductOnBasket() {
        UUID productId = UUID.randomUUID();
        Product mockProduct = mock(Product.class);
        when(storageService.getProductById(productId)).thenReturn(Optional.of(mockProduct));

        basketService.addProduct(productId);

        verify(storageService).getProductById(productId);
        verify(productBasket).addProduct(productId);
    }

    @Test
    void getUserBasket_WhenBasketIsEmpty_ReturnsEmptyUserBasket() {
        when(productBasket.getProductBasket()).thenReturn(Map.of());

        UserBasket result = basketService.getUserBasket();

        assertNotNull(result);
        assertTrue(result.getItems().isEmpty());
        assertEquals(0, result.getTotal());
        verify(productBasket).getProductBasket();
        verifyNoInteractions(storageService);
    }

    @Test
    void getUserBasket_WhenBasketHasItems_ReturnsUserBasketWithItems() {
        UUID productId = UUID.randomUUID();
        Product mockProduct = mock(Product.class);
        when(mockProduct.getPrice()).thenReturn(1000);
        when(mockProduct.getName()).thenReturn("Test Product");

        when(storageService.getProductById(productId)).thenReturn(Optional.of(mockProduct));
        when(productBasket.getProductBasket()).thenReturn(Map.of(productId, 2));

        UserBasket result = basketService.getUserBasket();

        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        assertEquals(2000, result.getTotal());

        BasketItem basketItem = result.getItems().get(0);
        assertEquals(mockProduct, basketItem.getProduct());
        assertEquals(2, basketItem.getQuantity());

        verify(productBasket).getProductBasket();
        verify(storageService).getProductById(productId);
    }

    @Test
    void getUserBasket_WhenProductNotFoundInStorage_ThrowsException() {
        UUID productId = UUID.randomUUID();
        when(productBasket.getProductBasket()).thenReturn(Map.of(productId, 1));
        when(storageService.getProductById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class, () -> {
            basketService.getUserBasket();
        });

        verify(productBasket).getProductBasket();
        verify(storageService).getProductById(productId);
    }

    @Test
    void getUserBasket_WhenMultipleItems_CalculatesTotalCorrectly() {
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();

        Product product1 = mock(Product.class);
        when(product1.getPrice()).thenReturn(1000);

        Product product2 = mock(Product.class);
        when(product2.getPrice()).thenReturn(500);

        when(storageService.getProductById(productId1)).thenReturn(Optional.of(product1));
        when(storageService.getProductById(productId2)).thenReturn(Optional.of(product2));
        when(productBasket.getProductBasket()).thenReturn(Map.of(
                productId1, 2,  // 2 * 1000 = 2000
                productId2, 3   // 3 * 500 = 1500
        ));                 // Total = 3500

        UserBasket result = basketService.getUserBasket();

        assertEquals(3500, result.getTotal());
        assertEquals(2, result.getItems().size());
    }
}