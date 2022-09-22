package com.example.demo.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tracker {
    @Id
    @GeneratedValue
    private int id;
    private LocalDate date;
    private int time;
    @ManyToOne
    // @Column(name = "user_time")
    private User user;
    @ManyToOne
    // @Column(name = "task_user")
    private Task task;
}
