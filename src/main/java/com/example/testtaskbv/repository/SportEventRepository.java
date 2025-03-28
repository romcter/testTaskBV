package com.example.testtaskbv.repository;

import com.example.testtaskbv.entity.SportEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportEventRepository extends JpaRepository<SportEvent, Long> {
    List<SportEvent> findBySettledFalseAndSportOrderByStartTime(String sport);
    List<SportEvent> findBySettledFalseOrderByStartTime();
}
