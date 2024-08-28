package com.wishlist.challenge.model.response;

import lombok.Builder;

@Builder
public record ProductResponse(String productId, String productName, Double productPrice) {}
