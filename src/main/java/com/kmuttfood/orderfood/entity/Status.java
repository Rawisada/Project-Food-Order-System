package com.kmuttfood.orderfood.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "status")
public class Status {

    @Id
    @Column(name = "status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, length = 45)
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /*
     * @OneToMany(cascade = CascadeType.ALL)
     * private Set<OrderCustomer> orderCustomers;
     * 
     * public Set<OrderCustomer> getOrderCustomers() {
     * return orderCustomers;
     * }
     * 
     * public void setOrderCustomers(Set<OrderCustomer> orderCustomers) {
     * this.orderCustomers = orderCustomers;
     * }
     */

    @Override
    public String toString() {
        return this.name;
    }

}
