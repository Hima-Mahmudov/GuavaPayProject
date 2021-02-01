package com.guavapay.order.repository;

import com.guavapay.order.entity.CardCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardCredentialsRepository extends JpaRepository<CardCredentials, Long> {
}
