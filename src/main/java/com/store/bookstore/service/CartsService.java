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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
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

    @Transactional
    public boolean removeBookToCart(Integer user_id, Integer book_id) {
        // Retrieve the user's cart
        Cart cart = this.cartsRepository.findByUserId(user_id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Find the item in the cart
        int itemIndex = -1;
        for (int i = 0; i < cart.getItems().size(); i++) {
            if (cart.getItems().get(i).getItem().getId() == book_id) {
                itemIndex = i;
                break;
            }
        }

        // If the item was not found in the cart
        if (itemIndex < 0) {
            throw new RuntimeException("Item Cart not found!");
        }


        // If the quantity is 1 or less, remove the item
        if (cart.getItems().get(itemIndex).getQuantity() - 1 > 0) {
            // Decrease the quantity of the item
            cart.getItems().get(itemIndex).setQuantity(cart.getItems().get(itemIndex).getQuantity() - 1);
            cart.getItems().get(itemIndex).setTotal_amount(cart.getItems().get(itemIndex).getQuantity() * cart.getItems().get(itemIndex).getItem().getPrice());

        } else {
            // Delete the item from the repository before removing from the list
            this.itemCartsRepository.delete(cart.getItems().get(itemIndex));

            // Remove the item from the cart's item list
            cart.getItems().remove(itemIndex);
        }

        cart.setTotal_amount(cart.getItems().stream()
                .mapToDouble(ItemCart::getTotal_amount)
                .sum());

        this.cartsRepository.save(cart);



        return true;
    }


    public CartDTO clearCart(Integer user_id){
        Cart toDeleteCart = this.cartsRepository.findByUserId(user_id).orElseThrow(()-> new RuntimeException("Cart not found!"));

        List<ItemCartDTO> itemCartDTOS = toDeleteCart.getItems().stream().map(itemcart->new ItemCartDTO(
                itemcart.getId(),
                itemcart.getCart().getId(),
                new BookDTO(itemcart.getItem().getId(),itemcart.getItem().getName(),itemcart.getItem().getDescription(),
                        itemcart.getItem().getAuthor(), itemcart.getItem().getCategory(), itemcart.getItem().getPrice()),
                itemcart.getQuantity(),
                itemcart.getTotal_amount()
        )).toList();

        toDeleteCart.setItems(new ArrayList<>());
        this.cartsRepository.save(toDeleteCart);

        return new CartDTO(toDeleteCart.getId(), itemCartDTOS, toDeleteCart.getTotal_amount(), user_id);
    }
}
