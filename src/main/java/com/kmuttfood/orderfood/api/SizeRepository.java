package com.kmuttfood.orderfood.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kmuttfood.orderfood.entity.Size;

public interface SizeRepository extends JpaRepository<Size, Integer> {

    @Query("SELECT s FROM Size s WHERE s.name = ?1")
    Size findByName(String name);

    @Modifying
    @Query("delete FROM Size s WHERE s.id = ?1")
    public void deleteById(Integer id);
}
