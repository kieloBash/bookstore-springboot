package com.store.bookstore.repository;

import com.store.bookstore.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartsRepository extends JpaRepository<Cart,Integer> {
    Optional<Cart> findByUserId(Integer user_id);
}
