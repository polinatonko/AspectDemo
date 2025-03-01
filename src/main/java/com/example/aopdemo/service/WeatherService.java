package com.example.aopdemo.service;

import com.example.aopdemo.model.WeatherDto;
import com.example.aopdemo.repository.WeatherRepository;
import com.example.aopdemo.model.Weather;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class WeatherService {
    private final WeatherRepository repository;
    private final Mapper mapper;

    public WeatherService(WeatherRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Weather create(WeatherDto dto) {
        var weather = mapper.toWeather(dto);
        return repository.create(weather);
    }

    public Optional<Weather> updateById(int id, Weather weather) {
        weather.setId(id);
        return repository.update(weather);
    }

    public Iterable<Weather> getAllByCityAndPeriod(String city, LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            startDate = LocalDate.now().minusDays(14);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        return repository.findAllByCityAndPeriod(city, startDate, endDate);
    }

    public Weather getForecast(String city, LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("The forecast date must be date in the future.");
        }
        return new Weather(0, date, city, Math.random() * 10, Math.random() * 100);
    }
}