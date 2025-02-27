package com.blessing333.springbasic.domain.customer;

import com.blessing333.springbasic.domain.customer.model.Customer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerFactory {
    public static Customer createCustomer(String name, String email) {
        UUID id = UUID.randomUUID();
        return Customer.customerBuilder()
                .customerId(id)
                .name(name)
                .email(email)
                .createdAt(LocalDateTime.now())
                .lastLoginAt(LocalDateTime.now())
                .build();
    }
}
