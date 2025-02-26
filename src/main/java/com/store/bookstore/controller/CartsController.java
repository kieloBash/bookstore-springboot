package com.store.bookstore.controller;

import com.store.bookstore.dto.CartDTO;
import com.store.bookstore.model.Cart;
import com.store.bookstore.model.ItemCart;
import com.store.bookstore.repository.CartsRepository;
import com.store.bookstore.service.CartsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
public class CartsController {

    private final CartsService cartsService;

    public CartsController(CartsService cartsService){
        this.cartsService = cartsService;
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<CartDTO> getUserCart(@PathVariable Integer user_id){
        return ResponseEntity.ok(this.cartsService.getUserCart(user_id));
    }

    @PostMapping("/add-item")
    public ResponseEntity<Void> addItemToCart(@RequestParam(name = "userId") Integer user_id,
                                                  @RequestParam(name = "bookId") Integer book_id){
        this.cartsService.addBookToCart(user_id,book_id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/delete-item")
    public ResponseEntity<Void> removeItemToCart(@RequestParam(name = "userId") Integer user_id,
                                              @RequestParam(name = "bookId") Integer book_id){

        this.cartsService.removeBookToCart(user_id,book_id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear-cart")
    public ResponseEntity<Void> clearCart(@RequestParam(name = "userId") Integer user_id){

        this.cartsService.clearCart(user_id);

        return ResponseEntity.noContent().build();
    }
}
