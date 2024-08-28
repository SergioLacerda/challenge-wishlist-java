package com.wishlist.challenge.service;

import com.wishlist.challenge.config.exception.BusinessException;
import com.wishlist.challenge.model.entity.Customer;
import com.wishlist.challenge.model.entity.Product;
import com.wishlist.challenge.model.entity.Wishlist;
import com.wishlist.challenge.model.request.AddProductRequest;
import com.wishlist.challenge.model.request.ProductRequest;
import com.wishlist.challenge.model.response.ProductResponse;
import com.wishlist.challenge.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishlistServiceTest {

    private static final String CUSTOMER_ID = "1";
    private static final String PRODUCT_ID = "1";
    private static final String PRODUCT_NAME = "Product 1";

    @InjectMocks
    private WishlistService wishlistService;

    @Mock
    private WishlistRepository wishlistRepository;

    @Test
    void addProduct_shouldAddNewProductToWishlist() {
        AddProductRequest addProductRequest = new AddProductRequest(PRODUCT_ID, PRODUCT_NAME, 10.0);
        Wishlist wishlist = createWishlist(new HashSet<>());

        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(wishlist));
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlist);

        wishlistService.addProduct(CUSTOMER_ID, addProductRequest);

        verify(wishlistRepository, times(1)).save(wishlist);
        assertTrue(wishlist.getProducts().stream().anyMatch(p -> p.getId().equals(addProductRequest.productId())));
    }

    @Test
    void addProduct_shouldCreateNewWishlistAndAddProduct() {
        AddProductRequest addProductRequest = new AddProductRequest(PRODUCT_ID, PRODUCT_NAME, 10.0);

        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.empty());
        when(wishlistRepository.save(any(Wishlist.class))).thenAnswer(invocation -> invocation.getArgument(0));

        wishlistService.addProduct(CUSTOMER_ID, addProductRequest);

        verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }

    @Test
    void testAddProduct_ExceedLimit_ThrowsException() {
        AddProductRequest request = new AddProductRequest(PRODUCT_ID, PRODUCT_NAME, 10.0);

        prepareMockListProducts();

        assertThrows(BusinessException.class, () -> wishlistService.addProduct(CUSTOMER_ID, request));
    }

    @Test
    void removeProduct_shouldRemoveProductFromWishlist() {
        ProductRequest request = new ProductRequest(PRODUCT_ID);
        Product product = Product.builder().id(PRODUCT_ID).build();
        Wishlist wishlist = createWishlist(new HashSet<>(Set.of(product)));

        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(wishlist));
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlist);

        wishlistService.removeProduct(CUSTOMER_ID, request);

        verify(wishlistRepository, times(1)).save(wishlist);
        assertTrue(wishlist.getProducts().isEmpty());
    }

    @Test
    void removeProduct_shouldThrowExceptionWhenWishlistNotFound() {
        ProductRequest request = new ProductRequest(PRODUCT_ID);

        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> wishlistService.removeProduct(CUSTOMER_ID, request));
    }

    @Test
    void getProducts_shouldReturnProductsFromWishlist() {
        Product product = Product.builder().id(PRODUCT_ID).name(PRODUCT_NAME).price(10.0).build();
        Wishlist wishlist = createWishlist(new HashSet<>(Set.of(product)));

        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(wishlist));

        Set<ProductResponse> products = wishlistService.getProducts(CUSTOMER_ID);

        assertEquals(1, products.size());
        assertTrue(products.stream().anyMatch(p -> p.productId().equals(product.getId())));
    }

    @Test
    void getProducts_shouldThrowExceptionWhenWishlistNotFound() {
        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> wishlistService.getProducts(CUSTOMER_ID));
    }

    @Test
    void isProductInWishlist_shouldReturnTrueWhenProductExists() {
        Product product = Product.builder().id(PRODUCT_ID).build();
        Wishlist wishlist = createWishlist(new HashSet<>(Set.of(product)));

        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(wishlist));

        boolean isInWishlist = wishlistService.isProductInWishlist(CUSTOMER_ID, PRODUCT_ID);

        assertTrue(isInWishlist);
    }

    @Test
    void isProductInWishlist_shouldReturnFalseWhenProductDoesNotExist() {
        Wishlist wishlist = createWishlist(new HashSet<>());

        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(wishlist));

        boolean isInWishlist = wishlistService.isProductInWishlist(CUSTOMER_ID, PRODUCT_ID);

        assertFalse(isInWishlist);
    }

    @Test
    void isProductInWishlist_shouldThrowExceptionWhenWishlistNotFound() {
        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> wishlistService.isProductInWishlist(CUSTOMER_ID, PRODUCT_ID));
    }

    private static Wishlist createWishlist(HashSet<Product> product) {
        return Wishlist.builder()
            .customer(Customer.builder().id(CUSTOMER_ID).build())
            .products(product)
            .build();
    }

    private void prepareMockListProducts() {
        Set<Product> products = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            products.add(Product.builder().name(String.valueOf(i)).name(String.valueOf(i)).build());
        }

        Wishlist wishlist = Wishlist.builder()
                .customer(Customer.builder().id(CUSTOMER_ID).build())
                .products(products)
                .build();

        when(wishlistRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(java.util.Optional.of(wishlist));
    }
}
