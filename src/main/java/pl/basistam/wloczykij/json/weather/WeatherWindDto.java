package pl.basistam.wloczykij.json.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherWindDto {
    private Double speed;
    private Double deg;
}
