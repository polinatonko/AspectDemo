package com.example.aopdemo.repository;

import com.example.aopdemo.model.Weather;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class WeatherRepository {
    private int id = 1;
    private final Map<Integer, Weather> repository = new HashMap<>();

    public Optional<Weather> findById(int id) {
        return Optional.of(repository.get(id));
    }

    public Iterable<Weather> findAllByCityAndPeriod(String city, LocalDate startDate, LocalDate endDate) {
        return repository.values()
                .stream()
                .filter(weather -> weather.getCity().equals(city) && weather.getDatetime().isAfter(startDate)
                        && weather.getDatetime().isBefore(endDate))
                .toList();
    }

    public Weather create(Weather weather) {
        weather.setId(id);
        repository.put(id++, weather);
        return  weather;
    }

    public Optional<Weather> update(Weather weather) {
        if (weather.getId() < 1) {
            throw new IllegalArgumentException("Weather id should be positive integer.");
        }
        if (!repository.containsKey(weather.getId())) {
            return Optional.empty();
        }
        repository.put(weather.getId(), weather);
        return Optional.of(weather);
    }

    public void deleteById(int id) {
        repository.remove(id);
    }

    @PostConstruct
    private void init() {
        repository.put(id,
                new Weather(id++, LocalDate.of(2025, 1, 1), "Minsk", 0, 40));
        repository.put(id,
                new Weather(id++, LocalDate.of(2025, 1, 2), "Minsk", 2, 38));
        repository.put(id,
                new Weather(id++, LocalDate.of(2025, 1, 3), "Minsk", 1, 30));
        repository.put(id,
                new Weather(id++, LocalDate.of(2025, 1, 4), "Minsk", -1, 25));
        repository.put(id,
                new Weather(id++, LocalDate.of(2025, 1, 1), "Grodno", 2, 42));
    }
}