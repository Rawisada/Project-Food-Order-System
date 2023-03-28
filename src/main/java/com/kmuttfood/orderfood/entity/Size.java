package com.kmuttfood.orderfood.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "size")
public class Size {
    @Id
    @Column(name = "size_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false, length = 64)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false, length = 10)
    private Integer price;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "size")
    private List<OrderCustomer> orderCustomer;

    public List<OrderCustomer> getOrderCustomer() {
        return orderCustomer;
    }

    public void setOrderCustomer(List<OrderCustomer> orderCustomer) {
        this.orderCustomer = orderCustomer;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
