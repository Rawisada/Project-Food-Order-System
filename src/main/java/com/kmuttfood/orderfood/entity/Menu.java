package com.kmuttfood.orderfood.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @Column(name = "menu_id")
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

    @Column(nullable = false, length = 120)
    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Column(length = 45, nullable = true)
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "menu")
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

    @Transient
    public String getMenuImagePath() {
        if (image == null || id == null)
            return null;
        return "/menu-image/" + id + "/" + image;
    }
}
