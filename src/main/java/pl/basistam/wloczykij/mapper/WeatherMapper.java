package pl.basistam.wloczykij.mapper;

import org.springframework.stereotype.Component;
import pl.basistam.wloczykij.json.weather.WeatherDto;
import pl.basistam.wloczykij.json.weather.WeatherSourceDto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WeatherMapper {

    public List<WeatherDto> sourcesToDtos(List<WeatherSourceDto> sources) {
        return sources.stream()
                .map(this::sourceToDto)
                .collect(Collectors.toList());
    }

    public WeatherDto sourceToDto(WeatherSourceDto source) {
        return WeatherDto.builder()
                .time(dateInSecondsToPrintableTime(source.getDt()))
                .date(dateInSecondsToPrintableDate(source.getDt()))
                .temperature(kelvinToCelsius(source.getMain().getTemp()))
                .temperatureMin(kelvinToCelsius(source.getMain().getTemp_min()))
                .temperatureMax(kelvinToCelsius(source.getMain().getTemp_max()))
                .pressure(source.getMain().getPressure())
                .humidity(source.getMain().getHumidity())
                .weather(source.getWeather().get(0).getMain())
                .weatherDescription(source.getWeather().get(0).getDescription())
                .clouds(source.getClouds().getAll())
                .windSpeed(source.getWind().getSpeed())
                .build();
    }

    private Double kelvinToCelsius(Double kelvin) {
        return kelvin - 273.15F;
    }

    private String dateInSecondsToPrintableTime(long dateInSeconds) {
        long dateInMs = dateInSeconds * 1000;
        DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(dateInMs);
    }

    private String dateInSecondsToPrintableDate(long dateInSeconds) {
        long dateInMs = dateInSeconds * 1000;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(dateInMs);
    }
}
