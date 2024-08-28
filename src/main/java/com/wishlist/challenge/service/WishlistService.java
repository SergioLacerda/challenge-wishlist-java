package com.wishlist.challenge.service;

import com.wishlist.challenge.config.exception.BusinessException;
import com.wishlist.challenge.model.entity.Customer;
import com.wishlist.challenge.model.entity.Product;
import com.wishlist.challenge.model.entity.Wishlist;
import com.wishlist.challenge.model.request.AddProductRequest;
import com.wishlist.challenge.model.request.ProductRequest;
import com.wishlist.challenge.model.response.ProductResponse;
import com.wishlist.challenge.repository.WishlistRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
public class WishlistService {

    private  static final String WISHLIST_NOT_FOUND = "Wishlist not found";
    private static final int PRODUCT_MAX_LIMIT_ALLOWED = 20;

    @Autowired
    private WishlistRepository wishlistRepository;

    public void addProduct(String customerId, AddProductRequest request) {
        Wishlist wishlist = wishlistRepository.findByCustomerId(customerId).
            orElseGet(() -> Wishlist.builder()
                .customer(Customer.builder().id(customerId).build())
                .products(new HashSet<>())
                .build());

        if (wishlist.getProducts().size() >= PRODUCT_MAX_LIMIT_ALLOWED) {
            throw new BusinessException("Cannot add more than 20 products to the wishlist");
        }

        Product product = Product.builder()
            .id(request.productId())
            .name(request.productName())
            .price(request.productPrice())
            .build();

        wishlist.getProducts().add(product);
        wishlistRepository.save(wishlist);
    }

    public void removeProduct(String customerId, ProductRequest request) {
        Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
            .orElseThrow(() -> new BusinessException(WISHLIST_NOT_FOUND));

        wishlist.getProducts().removeIf(p -> p.getId().equals(request.productId()));

        wishlistRepository.save(wishlist);
    }

    public Set<ProductResponse> getProducts(String customerId) {
        Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
            .orElseThrow(() -> new BusinessException(WISHLIST_NOT_FOUND));

        return wishlist.getProducts().stream()
            .map(product -> ProductResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productPrice(product.getPrice())
                .build())
            .collect(Collectors.toSet());
    }

    public boolean isProductInWishlist(String customerId, String productId) {
        Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
            .orElseThrow(() -> new BusinessException(WISHLIST_NOT_FOUND));

        return wishlist.getProducts().stream().anyMatch(product -> product.getId().equals(productId));
    }
}
