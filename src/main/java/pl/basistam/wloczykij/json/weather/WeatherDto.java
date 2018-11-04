package pl.basistam.wloczykij.json.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDto {
    private String time;
    private String date;
    private Double temperature;
    private Double temperatureMin;
    private Double temperatureMax;
    private Double pressure;
    private Double humidity;
    private String weather;
    private String weatherDescription;
    private Integer clouds;
    private Double windSpeed;
    private Double snow;
    private Double rain;
}
