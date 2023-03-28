package com.kmuttfood.orderfood.api;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmuttfood.orderfood.entity.Menu;
import com.kmuttfood.orderfood.entity.OrderCustomer;
import com.kmuttfood.orderfood.entity.Sidedish;
import com.kmuttfood.orderfood.entity.Size;
import com.kmuttfood.orderfood.entity.Status;
import com.kmuttfood.orderfood.entity.User;

@Service
public class OrderSevice {

    @Autowired
    private OrderCustomerRepository orderCustomerRepo;

    @Autowired
    private StatusRepository statusRepo;

    public List<OrderCustomer> listOrderCustomer(User user) {
        return orderCustomerRepo.findByUser(user);
    }

    // public List<OrderCustomer> listAllOrderCustomers() {
    // return orderCustomerRepo.findAll();
    // }

    public OrderCustomer getOrderCustomer(Integer id) {
        return orderCustomerRepo.findById(id).get();
    }

    public void saveOrderCustomer(OrderCustomer orderCustomer) {
        orderCustomerRepo.save(orderCustomer);

    }

    public void deleteOrderCustomer(Integer id) {
        orderCustomerRepo.deleteById(id);
    }

    public Status getsIDStatus(String status) {
        return statusRepo.findByName(status);
    }

    public void updateStatus(Status status, Integer orederId) {
        orderCustomerRepo.updateStatusOrder(status, orederId);
    }

    public void updateStatuslistOrders(Status status, Status status2) {
        orderCustomerRepo.updateStatuslistOrder(status, status2);
    }

    public void updateDatelistOrders(Date date, Status status2, User user) {
        orderCustomerRepo.updateDatelistOrder(date, status2, user);
    }

    public void updateTimelistOrders(Time time, Status status2, User user) {
        orderCustomerRepo.updateTimelistOrder(time, status2, user);
    }

    public List<OrderCustomer> findOrderofUserByStaus(Status status, User user) {
        return orderCustomerRepo.findOrderByUserandStatus(status, user);
    }

    public List<OrderCustomer> findOrderofUserByNotisStaus(Status status, User user, Date date) {
        return orderCustomerRepo.findOrderByUserandNotisStatus(status, user, date);
    }

    public List<OrderCustomer> findlistOrderByStausToday(Status status, Date date) {
        return orderCustomerRepo.findlistOrderByStatusToday(status, date);
    }

    public Integer findTotal(Date date, Status status) {
        return orderCustomerRepo.findtotal(date, status);

    }

    public Integer countNumberOfStatus(Status status, User user) {
        return orderCustomerRepo.countNumberOfStatus(status, user);

    }

    public User findUserByIdOrder(Integer id) {
        return orderCustomerRepo.findUserbyOrder(id);

    }

    public List<OrderCustomer> findlistOrderComplect(Date date, Status status) {
        return orderCustomerRepo.findlistSubmitOrder(date, status);
    }

    /*
     * public void deleteByMenu(Menu menu) {
     * orderCustomerRepo.deleteByMenu(menu);
     * }
     */

    public void createOrder(User user, Status status, OrderCustomer orderCustomer, Menu menu) {

        orderCustomer.setUser(user);
        orderCustomer.setMemu(menu);
        orderCustomer.setStatus(status);
        Sidedish sidedish = orderCustomer.getSidedish();
        Size size = orderCustomer.getSize();
        Integer price = menu.getPrice() + sidedish.getPrice() + size.getPrice();
        orderCustomer.setSubTotal(price * orderCustomer.getQuatity());
        orderCustomer.setOrderPrice(price);
        orderCustomerRepo.save(orderCustomer);

    }

}
