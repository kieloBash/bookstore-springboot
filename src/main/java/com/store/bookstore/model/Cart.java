package com.store.bookstore.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)  // Many carts can belong to one user (assuming Cart is associated with OurUsers)
    @JoinColumn(name = "user_id")  // Foreign key column in Cart referencing OurUsers
    private OurUsers user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  // One Cart can have many ItemCarts
    private List<ItemCart> items;

    private Double total_amount;

    public Cart(){}

    public Cart(OurUsers user) {
        this.user = user;
        this.items = new ArrayList<>();
        this.total_amount = 0d;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public List<ItemCart> getItems() {
        return items;
    }

    public void setItems(List<ItemCart> items) {
        this.items = items;
    }

    public OurUsers getUser() {
        return user;
    }

    public void setUser(OurUsers user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
