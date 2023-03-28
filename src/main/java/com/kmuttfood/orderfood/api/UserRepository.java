package com.kmuttfood.orderfood.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kmuttfood.orderfood.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findUserByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.enabled = ?1")
    public List<User> findListUser(Boolean enabled);

    @Modifying
    @Query("delete FROM User u WHERE u.id = ?1")
    public void deleteById(Long id);
}
