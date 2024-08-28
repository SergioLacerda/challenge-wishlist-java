package com.wishlist.challenge.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "customer")
public class Customer {

    @Id
    private String id;
    private String name;
}
