package com.store.bookstore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "itemorders")
public class ItemOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)  // Many ItemOrder can belong to one Order
    @JoinColumn(name = "order_id")  // Foreign key column in ItemOrder referencing Cart
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)  // Many ItemOrder can reference one Book
    @JoinColumn(name = "book_id")  // Foreign key column in ItemOrder referencing Book
    private Book item;

    private Integer quantity;
    private Double total_amount;

    public ItemOrder(){}

    public ItemOrder(Book book){
        this.item = book;
        this.quantity = 1;
        this.total_amount = 1 * book.getPrice();
    }

    public ItemOrder(Book book, Integer quantity){
        this.item = book;
        this.quantity = quantity;
        this.total_amount = quantity * book.getPrice();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
