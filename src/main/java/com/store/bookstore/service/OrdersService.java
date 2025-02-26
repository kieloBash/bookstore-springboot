package com.store.bookstore.service;

import com.store.bookstore.dto.CartDTO;
import com.store.bookstore.model.Book;
import com.store.bookstore.model.ItemOrder;
import com.store.bookstore.model.Order;
import com.store.bookstore.model.OurUsers;
import com.store.bookstore.repository.BooksRepository;
import com.store.bookstore.repository.OrdersRepository;
import com.store.bookstore.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;
    private final BooksRepository booksRepository;

    public OrdersService(OrdersRepository ordersRepository, UsersRepository usersRepository, BooksRepository booksRepository){
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
        this.booksRepository = booksRepository;
    }

    @Transactional
    public Order createOrder(CartDTO cartDTO){
        OurUsers currrentUser = this.usersRepository.findById(cartDTO.getUser_id())
                .orElseThrow(()->new RuntimeException("User not found!"));

        Order newOrder = new Order(currrentUser);
        List<ItemOrder> itemOrderList = cartDTO.getItems().stream()
                .map(itemCartDTO -> {
                    ItemOrder newItemOrder = new ItemOrder(
                            this.booksRepository.findById(itemCartDTO.getItem().getId()).orElseThrow(()->new RuntimeException("Book does not exist!")),
                            itemCartDTO.getQuantity());
                    newItemOrder.setOrder(newOrder);
                    return newItemOrder;
                })
                .toList();

        newOrder.setItems(itemOrderList);
        newOrder.setTotal_amount(cartDTO.getTotal_amount());

        this.ordersRepository.saveAndFlush(newOrder);

        return newOrder;
    }

    public List<Order> getAllUserOrder(Integer user_id){
        OurUsers currrentUser = this.usersRepository.findById(user_id)
                .orElseThrow(()->new RuntimeException("User not found!"));

        List<Order> orders = this.ordersRepository.findAllByUser_Id(user_id);

        return orders;
    }
}
