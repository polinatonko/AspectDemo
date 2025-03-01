package com.example.aopdemo.model;

import java.time.LocalDate;

public record WeatherDto(LocalDate datetime, String city, double temperature, double humidity) {}