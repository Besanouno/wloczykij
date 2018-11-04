package pl.basistam.wloczykij.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.basistam.wloczykij.json.weather.WeatherDto;
import pl.basistam.wloczykij.service.api.WeatherService;

import java.util.List;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public List<WeatherDto> getWeather() {
        return weatherService.getWeather();
    }
}
