package com.wishlist.challenge.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Builder
@Document(collection = "wishlists")
public class Wishlist {

    @Id
    private String id;
    private Customer customer;
    private Set<Product> products;
}
