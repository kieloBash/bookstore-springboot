package com.store.bookstore.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date orderDate;

    @ManyToOne(fetch = FetchType.LAZY)  // Many carts can belong to one user (assuming Order is associated with OurUsers)
    @JoinColumn(name = "user_id")  // Foreign key column in Order referencing OurUsers
    private OurUsers user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  // One Order can have many ItemOrders
    private List<ItemOrder> items;

    private Double total_amount;

    public Order(){}

    public Order(OurUsers user) {
        this.user = user;
        this.items = new ArrayList<>();
        this.total_amount = 0d;
        this.orderDate = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OurUsers getUser() {
        return user;
    }

    public void setUser(OurUsers user) {
        this.user = user;
    }

    public List<ItemOrder> getItems() {
        return items;
    }

    public void setItems(List<ItemOrder> items) {
        this.items = items;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }
}
