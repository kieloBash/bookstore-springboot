package com.store.bookstore.repository;

import com.store.bookstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order,Integer> {
    List<Order> findAllByUser_Id(Integer user_id);
}
