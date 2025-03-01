package com.example.aopdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weather {
    private int id;
    private LocalDate datetime;
    private String city;
    private double temperature;
    private double humidity;
}