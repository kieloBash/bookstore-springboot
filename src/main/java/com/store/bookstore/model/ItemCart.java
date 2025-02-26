package com.store.bookstore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "itemcarts")
public class ItemCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)  // Many ItemCart can belong to one Cart
    @JoinColumn(name = "cart_id")  // Foreign key column in ItemCart referencing Cart
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)  // Many ItemCart can reference one Book
    @JoinColumn(name = "book_id")  // Foreign key column in ItemCart referencing Book
    private Book item;

    private Integer quantity;
    private Double total_amount;

    public ItemCart(){}

    public ItemCart(Book book){
        this.item = book;
        this.quantity = 1;
        this.total_amount = 1 * book.getPrice();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Book getItem() {
        return item;
    }

    public void setItem(Book item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }
}
