package com.wishlist.challenge.model.request;

import lombok.Builder;

@Builder
public record AddProductRequest(String productId, String productName, Double productPrice) {}

