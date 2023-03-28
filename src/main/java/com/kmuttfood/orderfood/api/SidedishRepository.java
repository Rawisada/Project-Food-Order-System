package com.kmuttfood.orderfood.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kmuttfood.orderfood.entity.Sidedish;

public interface SidedishRepository extends JpaRepository<Sidedish, Integer> {
    @Query("SELECT s FROM Sidedish s WHERE s.name = ?1")
    Sidedish findByName(String name);

    @Modifying
    @Query("delete FROM Sidedish s WHERE s.id = ?1")
    public void deleteById(Integer id);

}
