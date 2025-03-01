package com.example.aopdemo.service;

import com.example.aopdemo.model.Weather;
import com.example.aopdemo.model.WeatherDto;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public Weather toWeather(WeatherDto dto) {
        return new Weather(0, dto.datetime(), dto.city(), dto.temperature(), dto.humidity());
    }
}