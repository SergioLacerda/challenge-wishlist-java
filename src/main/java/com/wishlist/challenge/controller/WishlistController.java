package com.wishlist.challenge.controller;

import com.wishlist.challenge.model.request.AddProductRequest;
import com.wishlist.challenge.model.request.ProductRequest;
import com.wishlist.challenge.model.response.ProductResponse;
import com.wishlist.challenge.service.WishlistService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Log4j2
@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/{customerId}/add")
    public ResponseEntity<String> addProduct(@PathVariable String customerId, @RequestBody AddProductRequest request) {
        wishlistService.addProduct(customerId, request);
        return ResponseEntity.ok("Product added to wishlist.");
    }

    @DeleteMapping("/{customerId}/remove")
    public ResponseEntity<String> removeProduct(@PathVariable String customerId, @RequestBody ProductRequest request) {
        wishlistService.removeProduct(customerId, request);
        return ResponseEntity.ok("Product removed from wishlist.");
    }

    @GetMapping("/{customerId}/products")
    public ResponseEntity<Set<ProductResponse>> getProducts(@PathVariable String customerId) {
        Set<ProductResponse> products = wishlistService.getProducts(customerId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{customerId}/check")
    public ResponseEntity<Boolean> isProductInWishlist(@PathVariable("customerId") String customerId,
                                                       @RequestParam("productId") String productId) {
        boolean exists = wishlistService.isProductInWishlist(customerId, productId);
        return ResponseEntity.ok(exists);
    }
}
