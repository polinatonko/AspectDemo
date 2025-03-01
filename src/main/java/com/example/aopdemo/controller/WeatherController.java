package com.example.aopdemo.controller;

import com.example.aopdemo.aspect.SecuredEndpoint;
import com.example.aopdemo.model.Weather;
import com.example.aopdemo.model.WeatherDto;
import com.example.aopdemo.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class WeatherController {
    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping("/history")
    public ResponseEntity<Iterable<Weather>> getHistory(@RequestParam String city, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        var history = service.getAllByCityAndPeriod(city, startDate, endDate);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/forecast")
    public ResponseEntity<Weather> getForecast(@RequestParam String city, @RequestParam LocalDate date) {
        var forecast = service.getForecast(city, date);
        return ResponseEntity.ok(forecast);
    }

    @PostMapping("/weather")
    @SecuredEndpoint("secretkey") // just for the demonstration
    public ResponseEntity<Weather> saveWeather(@RequestHeader("Authorization") String key, @RequestBody WeatherDto dto) {
        var savedRecord = service.create(dto);
        return ResponseEntity.ok(savedRecord);
    }
}