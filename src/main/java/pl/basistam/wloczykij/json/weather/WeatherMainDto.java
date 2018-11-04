package pl.basistam.wloczykij.json.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherMainDto {
    private Double temp;
    private Double temp_min;
    private Double temp_max;
    private Double pressure;
    private Double sea_level;
    private Double grnd_level;
    private Double humidity;
}
