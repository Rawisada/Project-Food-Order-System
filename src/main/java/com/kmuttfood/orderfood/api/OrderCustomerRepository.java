package com.kmuttfood.orderfood.api;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kmuttfood.orderfood.entity.Menu;
import com.kmuttfood.orderfood.entity.OrderCustomer;
import com.kmuttfood.orderfood.entity.Sidedish;
import com.kmuttfood.orderfood.entity.Size;
import com.kmuttfood.orderfood.entity.Status;
import com.kmuttfood.orderfood.entity.User;

@Repository
public interface OrderCustomerRepository extends JpaRepository<OrderCustomer, Integer> {
    public List<OrderCustomer> findByUser(User user);

    @Transactional
    @Modifying
    @Query("update OrderCustomer o set o.status = ?1 where o.id = ?2")
    public void updateStatusOrder(Status status, Integer orderId);

    @Transactional
    @Modifying
    @Query("update OrderCustomer o set o.status = ?1  where o.status = ?2")
    public void updateStatuslistOrder(Status status, Status status2);

    @Transactional
    @Modifying
    @Query("update OrderCustomer o set o.dateOrder = ?1  where o.status = ?2 and o.user = ?3")
    public void updateDatelistOrder(Date date, Status status2, User user);

    @Transactional
    @Modifying
    @Query("update OrderCustomer o set o.timeOrder  = ?1  where o.status = ?2 and o.user = ?3")
    public void updateTimelistOrder(Time time, Status status2, User user);

    @Transactional
    @Query("Select o from OrderCustomer o where o.status = ?1 and o.user = ?2")
    public List<OrderCustomer> findOrderByUserandStatus(Status status, User user);

    @Transactional
    @Query("Select o from OrderCustomer o where o.status != ?1 and o.user = ?2 and o.dateOrder = ?3 ")
    public List<OrderCustomer> findOrderByUserandNotisStatus(Status status, User user, Date date);

    @Transactional
    @Query("Select o from OrderCustomer o where o.status = ?1 and o.dateOrder = ?2 order by o.timeOrder")
    public List<OrderCustomer> findlistOrderByStatusToday(Status status, Date date);

    @Transactional
    @Query(value = "Select sum(o.SubTotal) from OrderCustomer o where o.dateOrder = ?1 and o.status = ?2")
    public Integer findtotal(Date date, Status status);

    @Transactional
    @Query("Select o from OrderCustomer o where o.dateOrder = ?1 and o.status = ?2 order by o.timeOrder")
    public List<OrderCustomer> findlistSubmitOrder(Date date, Status status);

    @Transactional
    @Query("Select o.user from OrderCustomer o where o.id = ?1 ")
    public User findUserbyOrder(Integer id);

    @Transactional
    @Query(value = "Select count(o) from OrderCustomer o where  o.status = ?1 and o.user = ?2")
    public Integer countNumberOfStatus(Status status, User uesr);

    @Modifying
    @Query("delete  FROM OrderCustomer o WHERE o.menu = ?1")
    public void deleteByMenu(Menu menu);

    @Modifying
    @Query("delete  FROM OrderCustomer o WHERE o.size = ?1")
    public void deleteBySize(Size size);

    @Modifying
    @Query("delete  FROM OrderCustomer o WHERE o.sidedish = ?1")
    public void deleteBySidedish(Sidedish sidedish);

    @Modifying
    @Query("delete  FROM OrderCustomer o WHERE o.user = ?1")
    public void deleteByUser(User user);

}
