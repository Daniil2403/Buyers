package com.example.kuzminov.repository;

import com.example.kuzminov.entity.Buyer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {

}