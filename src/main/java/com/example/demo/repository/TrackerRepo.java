package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Tracker;

@Repository
public interface TrackerRepo extends JpaRepository<Tracker,Integer>{
    
}
