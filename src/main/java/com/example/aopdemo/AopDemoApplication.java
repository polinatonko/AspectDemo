package com.example.aopdemo;

import com.example.aopdemo.config.AopDemoConfig;
import com.example.aopdemo.controller.WeatherController;
import com.example.aopdemo.model.WeatherDto;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class AopDemoApplication {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(AopDemoConfig.class)) {
            var controller = context.getBean(WeatherController.class);

            var history = controller.getHistory("Minsk", LocalDate.of(2025, 1, 1),
                    LocalDate.of(2025, 3, 1));
            System.out.println(history.getBody());

            var weatherDto = new WeatherDto(LocalDate.of(2025, 3, 1), "Grodno", 2, 42);
            controller.saveWeather("key", weatherDto);

            var forecast = controller.getForecast("Minsk", LocalDate.now().minusDays(10));
            System.out.println(forecast.getBody());
        }
    }

}
