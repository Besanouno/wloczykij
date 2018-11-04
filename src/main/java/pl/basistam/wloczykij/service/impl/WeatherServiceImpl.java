package pl.basistam.wloczykij.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.basistam.wloczykij.json.weather.WeatherDto;
import pl.basistam.wloczykij.json.weather.WeatherResponseDto;
import pl.basistam.wloczykij.mapper.WeatherMapper;
import pl.basistam.wloczykij.service.api.WeatherService;

import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final WeatherMapper weatherMapper;

    private List<WeatherDto> weather;
    private final static int TWO_HOURS_IN_MS = 1000 * 3600 * 2;
    private final static String OPEN_WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/forecast";
    private final static int TATRAS_ID = 6690164;
    private final static String APP_ID = "66650aa95e5cafa2d95e93e9d8acceb6";

    @Autowired
    public WeatherServiceImpl(WeatherMapper weatherMapper) {
        this.weatherMapper = weatherMapper;
    }

    @Override
    public List<WeatherDto> getWeather() {
        return weather;
    }

    @Scheduled(fixedRate = TWO_HOURS_IN_MS)
    public void updateWeather() {
        ResponseEntity<WeatherResponseDto> response
                = new RestTemplate().getForEntity(apiUrl(), WeatherResponseDto.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            this.weather = weatherMapper.sourcesToDtos(response.getBody().getList());
        }
    }

    private String apiUrl() {
        return UriComponentsBuilder.fromHttpUrl(OPEN_WEATHER_API_URL)
                .queryParam("id", TATRAS_ID)
                .queryParam("mode", "json")
                .queryParam("appid", APP_ID)
                .toUriString();
    }

}
