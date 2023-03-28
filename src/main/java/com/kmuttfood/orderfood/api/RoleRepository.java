package com.kmuttfood.orderfood.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kmuttfood.orderfood.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT r FROM Role r WHERE r.name = ?1")
    public Role findByName(String name);

    @Modifying
    @Query("delete FROM Role r WHERE r.id = ?1")
    public void deleteById(Integer id);

}
