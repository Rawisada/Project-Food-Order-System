package com.kmuttfood.orderfood.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kmuttfood.orderfood.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query("SELECT m FROM Menu m WHERE m.name = ?1")
    public Menu findByName(String name);

    @Modifying
    @Query("delete FROM Menu m WHERE m.id = ?1")
    public void deleteById(Integer id);

}
