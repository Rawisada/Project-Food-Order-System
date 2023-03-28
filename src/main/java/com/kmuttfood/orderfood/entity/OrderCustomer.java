package com.kmuttfood.orderfood.entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.*;

@Entity
@Table(name = "Order_Customer")
public class OrderCustomer {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne()
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(nullable = false, name = "status_id")
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(nullable = false, length = 10)
    private Integer orderPrice;

    public Integer getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }

    @Column(name = "date_order", nullable = true)
    private Date dateOrder;

    public Date getDateOrder() {
        return dateOrder;
    }

    @Column(name = "time_order", nullable = true)
    private Time timeOrder;

    public Time getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(Time timeOrder) {
        this.timeOrder = timeOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    @ManyToOne
    @JoinColumn(name = "menu", nullable = false)
    private Menu menu;

    public Menu getMemu() {
        return menu;
    }

    public void setMemu(Menu menu) {
        this.menu = menu;
    }

    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    private Size size;

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @ManyToOne
    @JoinColumn(name = "sidedish_id", nullable = false)
    private Sidedish sidedish;

    public Sidedish getSidedish() {
        return sidedish;
    }

    public void setSidedish(Sidedish sidedish) {
        this.sidedish = sidedish;
    }

    @Column(nullable = false, length = 5)
    private Integer quatity;

    public Integer getQuatity() {
        return quatity;
    }

    public void setQuatity(Integer quatity) {
        this.quatity = quatity;
    }

    @Column(nullable = false, length = 120)
    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Column(nullable = false)
    private Integer SubTotal;

    public Integer getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(Integer subTotal) {
        SubTotal = subTotal;
    }

}
