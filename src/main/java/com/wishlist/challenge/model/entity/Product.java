package com.wishlist.challenge.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "product")

public class Product {

    @Id
    private String id;
    private String name;
    private Double price;
}


