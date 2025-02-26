package com.store.bookstore.controller;

import com.store.bookstore.dto.CartDTO;
import com.store.bookstore.model.Order;
import com.store.bookstore.service.OrdersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {

    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService){
        this.ordersService = ordersService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody CartDTO cartDTO){

        Order createdOrder = this.ordersService.createOrder(cartDTO);

        if(createdOrder == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Order>> getAllUserOrders(@PathVariable(name = "userId") Integer user_id){
        return ResponseEntity.ok(this.ordersService.getAllUserOrder(user_id));
    }
}
