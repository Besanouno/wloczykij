package pl.basistam.wloczykij.service.api;

import pl.basistam.wloczykij.json.weather.WeatherDto;

import java.util.List;

public interface WeatherService {
    List<WeatherDto> getWeather();
}
