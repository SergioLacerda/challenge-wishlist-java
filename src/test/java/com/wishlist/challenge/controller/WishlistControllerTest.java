package com.wishlist.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wishlist.challenge.model.request.AddProductRequest;
import com.wishlist.challenge.model.request.ProductRequest;
import com.wishlist.challenge.model.response.ProductResponse;
import com.wishlist.challenge.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WishlistController.class)
class WishlistControllerTest {

    private static final String CUSTOMER_ID = "123";
    private static final String PRODUCT_ID = "456";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishlistService wishlistService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddProduct() throws Exception {
        AddProductRequest request = new AddProductRequest(PRODUCT_ID, "name", 10.0);
        doNothing().when(wishlistService).addProduct(CUSTOMER_ID, request);

        mockMvc.perform(post("/api/wishlist/{customerId}/add", CUSTOMER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Product added to wishlist."));
    }

    @Test
    void testRemoveProduct() throws Exception {
        ProductRequest request = new ProductRequest("product123");

        doNothing().when(wishlistService).removeProduct(CUSTOMER_ID, request);

        mockMvc.perform(delete("/api/wishlist/{customerId}/remove", CUSTOMER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Product removed from wishlist."));
    }

    @Test
    void testGetProducts() throws Exception {
        Set<ProductResponse> products = Collections.singleton(new ProductResponse("456", "Product Name", 10.0));
        when(wishlistService.getProducts(CUSTOMER_ID)).thenReturn(products);

        mockMvc.perform(get("/api/wishlist/{customerId}/products", CUSTOMER_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(products)));
    }

    @Test
    void testIsProductInWishlist() throws Exception {
        when(wishlistService.isProductInWishlist(CUSTOMER_ID, PRODUCT_ID)).thenReturn(true);

        mockMvc.perform(get("/api/wishlist/{customerId}/check?productId={productId}", CUSTOMER_ID, PRODUCT_ID))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
