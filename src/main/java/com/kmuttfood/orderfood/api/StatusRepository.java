package com.kmuttfood.orderfood.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kmuttfood.orderfood.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    @Query("SELECT s FROM Status s WHERE s.name = ?1")
    Status findByName(String name);

}
