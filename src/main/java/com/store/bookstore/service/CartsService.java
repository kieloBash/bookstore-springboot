package com.store.bookstore.service;

import com.store.bookstore.dto.BookDTO;
import com.store.bookstore.dto.CartDTO;
import com.store.bookstore.dto.ItemCartDTO;
import com.store.bookstore.model.Book;
import com.store.bookstore.model.Cart;
import com.store.bookstore.model.ItemCart;
import com.store.bookstore.model.OurUsers;
import com.store.bookstore.repository.BooksRepository;
import com.store.bookstore.repository.CartsRepository;
import com.store.bookstore.repository.ItemCartsRepository;
import com.store.bookstore.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartsService {
    private final CartsRepository cartsRepository;

    private final UsersRepository usersRepository;

    private final BooksRepository booksRepository;

    private final ItemCartsRepository itemCartsRepository;

    public CartsService(CartsRepository cartsRepository, UsersRepository usersRepository, BooksRepository booksRepository, ItemCartsRepository itemCartsRepository) {
        this.cartsRepository = cartsRepository;
        this.usersRepository = usersRepository;
        this.booksRepository = booksRepository;
        this.itemCartsRepository = itemCartsRepository;
    }

    public CartDTO getUserCart(Integer user_id){
        OurUsers user = this.usersRepository.findById(user_id).orElseThrow(()->  new RuntimeException("User not found"));
        Cart cart = this.cartsRepository.findByUserId(user_id).orElseGet(()->new Cart(user));

        List<ItemCartDTO> itemCartDTOS = cart.getItems().stream().map(itemcart->new ItemCartDTO(
                itemcart.getId(),
                itemcart.getCart().getId(),
                new BookDTO(itemcart.getItem().getId(),itemcart.getItem().getName(),itemcart.getItem().getDescription(),
                        itemcart.getItem().getAuthor(), itemcart.getItem().getCategory(), itemcart.getItem().getPrice()),
                itemcart.getQuantity(),
                itemcart.getTotal_amount()
        )).toList();

        return new CartDTO(cart.getId(), itemCartDTOS, cart.getTotal_amount(), user_id);
    }

    public void addBookToCart(Integer user_id, Integer book_id){
        OurUsers user = this.usersRepository.findById(user_id).orElseThrow(()->  new RuntimeException("User not found"));

        Cart cart = this.cartsRepository.findByUserId(user_id).orElseGet(()->new Cart(user));

        Book book = this.booksRepository.findById(book_id).orElseThrow(()-> new RuntimeException("Book not found"));

        Optional<ItemCart> itemInCart = this.itemCartsRepository.findByCart_IdAndItem_Id(cart.getId(),book.getId());

        if(itemInCart.isPresent()){
            ItemCart itemCart = itemInCart.get();
            itemCart.setQuantity(itemCart.getQuantity() + 1);
            itemCart.setTotal_amount(itemCart.getQuantity() * itemCart.getItem().getPrice());
        }else{
            ItemCart newItemCart = new ItemCart(book);
            newItemCart.setCart(cart);
            this.itemCartsRepository.save(newItemCart);
        }

        cart.setTotal_amount(cart.getItems().stream()
                .mapToDouble(ItemCart::getTotal_amount)
                .sum());

        this.cartsRepository.save(cart);
    }
}
