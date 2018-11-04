package pl.basistam.wloczykij.json.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherSourceDto {
    private long dt;
    private WeatherMainDto main;
    private List<WeatherDetailsDto> weather;
    private WeatherCloudsDto clouds;
    private WeatherWindDto wind;
}
